package com.fm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fm.model.M3Error;
import com.fm.model.M3Missing;
import com.fm.util.AckDBUtil;
import com.fm.util.FMDBUtil;

public class FileMonitoringDao {

	public int getTotalM3MissingFile() {

        String sql =
                "SELECT COUNT(*) " +
                "FROM M3_Missing " +
                "WHERE STATUS_FG='Y' and  WMS_ORDERNO <>'-';";

        try (
                Connection conn = FMDBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to get total M3 missing file",
                    e
            );
        }

        return 0;
    }
	
	 public List<M3Missing> getM3MissingFileList() throws Exception {

	        List<M3Missing> list = new ArrayList<>();


	        String sql =
	                "SELECT " +
	                "    ID, " +
	                "    WMS_OrderNo, " +
	                "    [DATE], " +
	                "    ITEM_CODE, " +
	                "    PICKED_QTY, " +
	                "    STORER_CODE " +
	                "FROM M3_Missing WHERE STATUS_FG ='Y' AND WMS_ORDERNO <>'-' " +
	                "ORDER BY [DATE] DESC";


	        try (
	            Connection conn = FMDBUtil.getConnection();
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ResultSet rs = ps.executeQuery()
	        ) {


	            while (rs.next()) {

	                M3Missing missing =
	                        new M3Missing();


	                // =================================================
	                // ID
	                // =================================================

	                missing.setId(
	                        rs.getInt("ID")
	                );


	                // =================================================
	                // WMS ORDER NO
	                // =================================================

	                missing.setWmsOrderNo(
	                        rs.getString("WMS_OrderNo")
	                );


	                // =================================================
	                // DATE
	                // =================================================

	                missing.setDate(
	                        rs.getTimestamp("DATE")
	                );


	                // =================================================
	                // ITEM CODE
	                // =================================================

	                missing.setItemCode(
	                        rs.getString("ITEM_CODE")
	                );


	                // =================================================
	                // QTY
	                // =================================================

	                missing.setQty(
	                        rs.getDouble("PICKED_QTY")
	                );


	                // =================================================
	                // STORER CODE
	                // =================================================

	                missing.setStorerCode(
	                        rs.getString("STORER_CODE")
	                );


	                list.add(missing);
	            }
	        }


	        return list;
	    }

    /**
     * Get total M3 error files.
     */
    public int getTotalM3ErrorFile() {

        String sql =
                "SELECT COUNT(*) " +
                "FROM T3_ErrorFileLog " +
                "WHERE STATUS_FLAG ='Y'";

        try (
                Connection conn = AckDBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to get total M3 error file",
                    e
            );
        }

        return 0;
    }
    
    
    public List<M3Error> getM3Errors() throws Exception {

        List<M3Error> list = new ArrayList<>();

        String sql =
            "SELECT ID, FileName, FilePath, DetectedTime, Status_Flag " +
            "FROM T3_ErrorFileLog WHERE status_flag='Y' " +
            "ORDER BY DetectedTime DESC";

        try (
            Connection conn = AckDBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                M3Error error = new M3Error();

                error.setId(rs.getInt("ID"));
                error.setFileName(rs.getString("FileName"));
                error.setFilePath(rs.getString("FilePath"));
                error.setDetectedTime(rs.getTimestamp("DetectedTime"));
                error.setStatusFlag(rs.getString("Status_Flag"));

                list.add(error);
            }
        }

        return list;
    }
    
    
    public M3Error getM3ErrorById(int id) throws Exception {

        String sql =
            "SELECT ID, FileName, FilePath, DetectedTime, Status_Flag " +
            "FROM T3_ErrorFileLog " +
            "WHERE ID = ?";

        try (
            Connection conn = AckDBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    M3Error error = new M3Error();

                    error.setId(rs.getInt("ID"));
                    error.setFileName(rs.getString("FileName"));
                    error.setFilePath(rs.getString("FilePath"));
                    error.setDetectedTime(
                        rs.getTimestamp("DetectedTime")
                    );
                    error.setStatusFlag(
                        rs.getString("Status_Flag")
                    );

                    return error;
                }
            }
        }

        return null;
    }
    
    public void updateM3ErrorStatus(
            int id,
            String status) throws Exception {

        String sql =
            "UPDATE T3_ErrorFileLog " +
            "SET Status_Flag = ? , UpdatedTime = GETDATE()   " +
            "WHERE ID = ?";

        try (
            Connection conn = AckDBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
    }
    
    
    public String getWmsOrderNoById(int id) throws Exception {
    	
        String wms_orderno = null;

        String sql =
                "SELECT WMS_OrderNo " +
                "FROM M3_Missing " +
                "WHERE ID = ?";

        try (
            Connection conn = FMDBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                	wms_orderno = rs.getString("WMS_OrderNo");
                }
            }
        }

        return wms_orderno;
    }
    
    
    public void updateM3FileMissingStatus(
            int id,
            String status,
            String remark) throws Exception {

        String sql =
            "UPDATE dbo.M3_Missing " +
            "SET Status_Fg = ?, " +
            "    LASTUPDATED_DATE = GETDATE(), " +
            "    remarks = ? " +
            "WHERE ID = ?";

        try (
            Connection conn = FMDBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setString(2, remark);
            ps.setInt(3, id);

            ps.executeUpdate();
        }
    }
}
