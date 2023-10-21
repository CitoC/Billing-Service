package com.gitcodings.stack.billing.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitcodings.stack.core.result.Result;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class BillingRepo
{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public BillingRepo(NamedParameterJdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public boolean itemExists(Long movieId, Long userId) {
        String sql = "SELECT COUNT(*) FROM billing.cart WHERE movie_id = :movieId AND user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("movieId", movieId);
        params.addValue("userId", userId);

        Integer count = null;
        try {
            count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        // what if count is 2? It will return false but that's not possible.
        return (count != null && count == 1);
    }

    public void insertItem(Long movieId, Integer quantity, Long userId) {
        String sql = "INSERT INTO billing.cart (user_id, movie_id, quantity) VALUES (:userId, :movieId, :quantity)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("movieId", movieId);
        params.addValue("userId", userId);
        params.addValue("quantity", quantity);

        jdbcTemplate.update(sql, params);
    }

    public void updateItem(Long movieId, Integer quantity, Long userId) {
        String sql = "UPDATE billing.cart SET quantity = :quantity WHERE movie_id = :movieId AND user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("quantity", quantity);
        params.addValue("movieId", movieId);
        params.addValue("userId", userId);

        jdbcTemplate.update(sql, params);
    }
}
