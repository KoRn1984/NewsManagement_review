package by.itacademy.matveenko.jd2.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import by.itacademy.matveenko.jd2.bean.News;
import by.itacademy.matveenko.jd2.dao.DaoException;
import by.itacademy.matveenko.jd2.dao.INewsDao;
import by.itacademy.matveenko.jd2.dao.NewsDaoException;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPool;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPoolException;

public class NewsDao implements INewsDao {
	private final UserDao userDao = new UserDao();	

	@Override
	public List<News> getLatestList(int pageSize) throws NewsDaoException  {
		List<News> newsLatestList = new ArrayList<>();
		int startSize = pageSize;
		String selectNewsLatestList = "SELECT * FROM news ORDER BY date DESC LIMIT " + startSize;	 
	        try (Connection connection = ConnectionPool.getInstance().takeConnection();
	        	PreparedStatement ps = connection.prepareStatement(selectNewsLatestList)) {
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {	                	
	    				News latestNews = new News.Builder()
	    						.withId(rs.getInt("id"))
	                            .withTitle(rs.getString("title"))
	                            .withBrief(rs.getString("brief"))
	                            .withContent(rs.getString("content"))
	                            .withDate(LocalDate.parse(rs.getString("date")))
	                            .withAuthor(userDao.findById(rs.getInt("reporter")))
	                            .build();
	    				newsLatestList.add(latestNews);
	    			}	    						
	        }	        
	    } catch (SQLException | ConnectionPoolException | DaoException e) {	    	
	    	throw new NewsDaoException(e);
	    	}
	        return newsLatestList;
	 }				

	@Override
	public List<News> getNewsList(Integer pageNumber, Integer pageSize) throws NewsDaoException {
		List<News> newsList = new ArrayList<>();
		int startSize = (pageNumber - 1) * pageSize;
		String selectNewsList = "SELECT * FROM news ORDER BY date DESC LIMIT " + startSize + "," + pageSize; 
	        try (Connection connection = ConnectionPool.getInstance().takeConnection();
	        	PreparedStatement ps = connection.prepareStatement(selectNewsList)) {
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {	                	
	    				News news = new News.Builder()
	    						.withId(rs.getInt("id"))
	                            .withTitle(rs.getString("title"))
	                            .withBrief(rs.getString("brief"))
	                            .withContent(rs.getString("content"))
	                            .withDate(LocalDate.parse(rs.getString("date")))
	                            .withAuthor(userDao.findById(rs.getInt("reporter")))
	                            .build();
	    				newsList.add(news);
	    			}	    						
	        }	        
	    } catch (SQLException | ConnectionPoolException | DaoException e) {	    	
	    	throw new NewsDaoException(e);
	    	}
	        return newsList;
	 }				
	
	String selectNewsById = "SELECT * FROM news WHERE id = ?";
	@Override
	public News fetchById(Integer idNews) throws NewsDaoException {
		News news = null;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
	        PreparedStatement ps = connection.prepareStatement(selectNewsById)) {
			ps.setInt(1, idNews);
			try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {                	
                	news = new News.Builder()
    						.withId(rs.getInt("id"))
                            .withTitle(rs.getString("title"))
                            .withBrief(rs.getString("brief"))
                            .withContent(rs.getString("content"))
                            .withDate(LocalDate.parse(rs.getString("date")))
                            .withAuthor(userDao.findById(rs.getInt("reporter")))
                            .build();
    				}
                }
			} catch (SQLException | ConnectionPoolException | DaoException e) {				
				throw new NewsDaoException(e);
			}
			return news;
	}
	
	String insertNews = "INSERT INTO news(title, brief, content, date, reporter) VALUES (?,?,?,?,?)";
	@Override
	public int addNews(News news) throws NewsDaoException {
		int row = 0;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		    PreparedStatement ps = connection.prepareStatement(insertNews)) {
			ps.setString(1, news.getTitle());
            ps.setString(2, news.getBrief());
            ps.setString(3, news.getContent());            
            ps.setString(4, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ps.setInt(5, news.getAuthor().getId());          
            row = ps.executeUpdate();
            if (row == 0) {
				throw new NewsDaoException("News not saved!");
			}            
				} catch (SQLException | ConnectionPoolException e) {					
					throw new NewsDaoException(e);
				}
				return row;
		}
	
	String updateNews = "UPDATE news SET title = ?, brief = ?, content = ?, date = ?, reporter = ? WHERE id = ?";
	@Override
	public boolean updateNews(News news) throws NewsDaoException {
		int row = 0;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		    PreparedStatement ps = connection.prepareStatement(updateNews)) {
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getContent());
			ps.setString(4, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ps.setInt(5, news.getAuthor().getId());
            ps.setInt(6, news.getId());
			row = ps.executeUpdate();
			if (row == 0) {
				return false;
				}
			        return true;
			        } catch (SQLException | ConnectionPoolException e) {						
						throw new NewsDaoException(e);
					}					
	}
	
	String deleteNews = "DELETE FROM news WHERE id IN (?)";
	@Override
	public boolean deleteNewses(String[] idNewses) throws NewsDaoException {
		int row = 0;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
			try {
				connection.setAutoCommit(false);
		        PreparedStatement ps = connection.prepareStatement(deleteNews);
		        for (int i = 0; i < idNewses.length; i++) {
			    ps.setInt(1, Integer.parseInt (idNewses [i]));
			    row = ps.executeUpdate();
			    if (row == 0) {
			    	return false;
			    	}
			    }
		        connection.commit();
		        connection.setAutoCommit(true);
		        } catch (SQLException e) {
		        	connection.rollback();		        			        	
		        	}
			} catch (SQLException | ConnectionPoolException e) {
				throw new NewsDaoException(e);
		}
		return true;
	}
}