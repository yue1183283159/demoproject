package com.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class GenderTypeHandler implements TypeHandler<Gender> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Gender parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getCode());

	}

	@Override
	public Gender getResult(ResultSet rs, String columnName) throws SQLException {
		int code = rs.getInt(columnName);
		return Gender.valueOf(code);
	}

	@Override
	public Gender getResult(ResultSet rs, int columnIndex) throws SQLException {
		int code = rs.getInt(columnIndex);
		return Gender.valueOf(code);
	}

	@Override
	public Gender getResult(CallableStatement cs, int columnIndex) throws SQLException {
		int code = cs.getInt(columnIndex);
		return Gender.valueOf(code);
	}

}
