package com.gitcodings.stack.billing.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitcodings.stack.billing.model.data.CartItem;
import com.gitcodings.stack.core.result.Result;
import com.nimbusds.jwt.SignedJWT;
import com.stripe.param.PlanCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public void deleteItem(Long movieId, Long userId) {
        String sql = "DELETE FROM billing.cart WHERE movie_id = :movieId AND user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("movieId", movieId);
        params.addValue("userId", userId);

        jdbcTemplate.update(sql, params);
    }

    public List<CartItem> getItems(Long userId, boolean isPremium) {
        String sql = "SELECT mp.unit_price, c.quantity, c.movie_id, m.title, m.backdrop_path, m.poster_path, mp.premium_discount" +
                " FROM billing.cart AS c" +
                " JOIN movies.movie AS m ON m.id = c.movie_id" +
                " JOIN billing.movie_price AS mp ON mp.movie_id = c.movie_id" +
                " WHERE user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        List<CartItem> items = null;
        items = jdbcTemplate.query(sql, params, (rs, rowNum) ->
                new CartItem()
                        .setUnitPrice(calcUnitPrice(rs, isPremium))
                        .setQuantity(rs.getInt("quantity"))
                        .setMovieId(rs.getLong("movie_id"))
                        .setMovieTitle(rs.getString("title"))
                        .setBackdropPath(rs.getString("backdrop_path"))
                        .setPosterPath(rs.getString("poster_path"))
        );
        return items;
    }

    private BigDecimal calcUnitPrice(ResultSet rs, boolean isPremium) throws SQLException {
        if (isPremium) {
            int discount = rs.getInt("premium_discount");
            BigDecimal unitPrice = rs.getBigDecimal("unit_price").setScale(2, RoundingMode.HALF_UP);
            // DiscountedUnitPrice = ( UnitPrice * (1 - (Discount / 100.0)))
            return unitPrice.multiply(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100.0))))
                    .setScale(2, RoundingMode.DOWN);
        }
        else {
            return rs.getBigDecimal("unit_price").setScale(2, RoundingMode.DOWN);
        }
    }
}
