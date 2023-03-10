package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{

    //DB에 연결하기 위해 datasource가 필요
    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        //변수보다 상수로 빼놓는게 좋다
        String sql = "insert into member(name) values(?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; //쿼리수행결과 받음

        try {
            //연결정보 가져오기
            conn = getConnection();
            /*RETURN_GENERATED_KEYS
             *: member 테이블 생성시 generated by default as identity 작성했던 id값을 insert 수행시 db에서 id값을 생성해
             *  id값을 저장한다. */
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //sql value인자 정보
            pstmt.setString(1, member.getName());

            //db에 실제 저장쿼리 전송
            pstmt.executeUpdate();
            //RETURN_GENERATED_KEY와 함꼐 사용할 수 있는 함수이다. db에서 생성 후, insert한 id값을 반환한다.
            rs = pstmt.getGeneratedKeys();

            //resultset에 데이터가 있으면 꺼내고, 실패하면 exception처리
            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);

        } finally {
            //db연결 정보는 외부 네트워크와 연결되기 때문에 사용 후 연결을 끊어줘야 한다. (리소스반환)
            //연결안끊으면 connection이 계속 생성되고 쌓여서 문제가 될 수 있다.
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            //db에 실제 조회쿼리 전송
            rs = pstmt.executeQuery();

            if(rs.next()) {
                //조회결과가 있으면 member객체 생성해 데이터 반환
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    //springframework를 통해 dataconnection을 쓸 때는 DataSourceUtils를 통해서 connection을 획득해야 한다.
    //예를들면 이전에 transaction에 걸린 경우 해당 connection을 유지해주기 위해 사용한다.
    //직접 사용할 일은 없으니 참고차 알아둘 것
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        //연결을 끊어줄 때는 생성시의 역순으로 끊어준다.
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //connection을 닫을 때도 DataSourceUtils를 통해 release 해주어야 한다.
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
