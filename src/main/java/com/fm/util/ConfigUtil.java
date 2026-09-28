package com.fm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigUtil {

    private static ConcurrentHashMap<String, String> cache =
            new ConcurrentHashMap<>();

    /**
     * Get Config Value
     */
    public static String getValue(String key) {

        // Return cache if exists
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        String sql =
            "SELECT ConfigValue FROM System_Config WHERE ConfigKey=?";

        try (
            Connection conn = AckDBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, key);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String value = rs.getString("ConfigValue");

                cache.put(key, value);

                return value;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Reload all config
     */
    public static void clearCache() {
        cache.clear();
    }

}