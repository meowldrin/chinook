package limmen.integration.repositories;

import limmen.integration.entities.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Kim Hammar on 2016-03-22.
 */
@Repository
public class TrackRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbc;

    public Track getTrack(int trackId) {
        return jdbc.queryForObject("SELECT * FROM \"Track\" WHERE \"TrackId\"=?", trackMapper, trackId);
    }

    public List<Track> getAllTracks(){
        log.info("getAllTracks from Database");
        return jdbc.query("SELECT * FROM \"Track\";", trackMapper);
    }

    private static final RowMapper<Track> trackMapper = new RowMapper<Track>() {
        public Track mapRow(ResultSet rs, int rowNum) throws SQLException {
            Track track = new Track(rs.getInt("TrackId"), rs.getString("Name"), rs.getInt("AlbumId"),
                    rs.getInt("MediaTypeId"), rs.getInt("GenreId"), rs.getString("Composer"), rs.getInt("Milliseconds"),
                    rs.getInt("Bytes"), rs.getFloat("UnitPrice"));
            return track;
        }
    };
}