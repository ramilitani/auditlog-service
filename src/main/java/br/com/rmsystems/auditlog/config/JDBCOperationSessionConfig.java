package br.com.rmsystems.auditlog.config;

import br.com.rmsystems.auditlog.dto.JSession;
import br.com.rmsystems.auditlog.exception.ValidateException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.session.jdbc.JdbcOperationsSessionRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.SerializationUtils;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCOperationSessionConfig extends JdbcOperationsSessionRepository {

    public JDBCOperationSessionConfig(JdbcOperations jdbcOperations, PlatformTransactionManager transactionManager) {
        super(jdbcOperations, transactionManager);
        setDefaultMaxInactiveInterval(10800);
        setLobHandler(new JDBCLobHandlerConfig());
    }

    private class JDBCLobHandlerConfig extends DefaultLobHandler {

        @Override
        public LobCreator getLobCreator() {
            return new JDBCLobCreatorConfig();
        }

        @Override
        public byte[] getBlobAsBytes(ResultSet rs, int columnIndex) throws SQLException {

            byte[] sessionAttributes = null;

            try {
                ObjectMapper mapper = new ObjectMapper();
                String jsonAttribute = rs.getString(columnIndex);
                JSession session = mapper.readValue(jsonAttribute, JSession.class);
                sessionAttributes = SerializationUtils.serialize(session);

            } catch(IOException e) {
                throw new ValidateException("Failed to convert json atributte to JSession. Error: " + e.getMessage());
            }

            return sessionAttributes;
        }
    }

    private class JDBCLobCreatorConfig implements LobCreator {

        @Override
        public void setBlobAsBytes(PreparedStatement ps, int i, byte[] bytes) throws SQLException {

            try {
              ObjectMapper mapper = new ObjectMapper();
              ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(bytes));
              JSession session = (JSession) in.readObject();
              ps.setString(i, mapper.writeValueAsString(session));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void setBlobAsBinaryStream(PreparedStatement preparedStatement, int i, InputStream inputStream, int i1) throws SQLException {

        }

        @Override
        public void setClobAsString(PreparedStatement preparedStatement, int i, String s) throws SQLException {

        }

        @Override
        public void setClobAsAsciiStream(PreparedStatement preparedStatement, int i, InputStream inputStream, int i1) throws SQLException {

        }

        @Override
        public void setClobAsCharacterStream(PreparedStatement preparedStatement, int i, Reader reader, int i1) throws SQLException {

        }

        @Override
        public void close() {

        }
    }
}