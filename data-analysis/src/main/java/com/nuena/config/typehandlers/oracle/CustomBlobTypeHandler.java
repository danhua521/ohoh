package com.nuena.config.typehandlers.oracle;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description:
 * @author: rengb
 * @time: 2020/4/9 11:03
 */
@MappedTypes(Blob.class)
public class CustomBlobTypeHandler extends BaseTypeHandler<Blob> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    Blob parameter, JdbcType jdbcType) throws SQLException {
        InputStream is;
        is = parameter.getBinaryStream();
        try {
            ps.setBinaryStream(i, is, is.available());
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Blob getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return rs.getBlob(columnName);
    }

    @Override
    public Blob getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.getBlob(columnIndex);
    }

    @Override
    public Blob getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getBlob(columnIndex);
    }

}