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
import by.itacademy.matveenko.jd2.controller.NewsParameterName;
import by.itacademy.matveenko.jd2.dao.DaoException;
import by.itacademy.matveenko.jd2.dao.INewsDao;
import by.itacademy.matveenko.jd2.dao.NewsDaoException;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPool;
import by.itacademy.matveenko.jd2.dao.connectionpool.ConnectionPoolException;

public class NewsDao implements INewsDao {
	private final UserDao userDao = new UserDao();
	//private final IUserDao userDao = DaoProvider.getInstance().getUserDao();
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String MESSAGE_EXCEPTION = "News not saved!";
	
	private static final String SELECT_NEWS_LATEST_LIST = "SELECT * FROM news ORDER BY date DESC LIMIT ";
	@Override
	public List<News> getLatestList(int pageSize) throws NewsDaoException  {
		List<News> newsLatestList = new ArrayList<>();
		int startSize = pageSize;		
		StringBuilder strBuilder = new StringBuilder(SELECT_NEWS_LATEST_LIST);
		strBuilder.append(startSize);		
	        try (Connection connection = ConnectionPool.getInstance().takeConnection();
	        	PreparedStatement ps = connection.prepareStatement(strBuilder.toString())) {
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {	                	
	    				News latestNews = new News.Builder()
	    						.withId(rs.getInt(NewsParameterName.JSP_ID_NEWS))
	                            .withTitle(rs.getString(NewsParameterName.JSP_TITLE_NEWS))
	                            .withBrief(rs.getString(NewsParameterName.JSP_BRIEF_NEWS))
	                            .withContent(rs.getString(NewsParameterName.JSP_CONTENT_NEWS))
	                            .withDate(LocalDate.parse(rs.getString(NewsParameterName.JSP_DATE_NEWS)))
	                            .withAuthor(userDao.findById(rs.getInt(NewsParameterName.JSP_ID_REPORTER)))
	                            .build();
	    				newsLatestList.add(latestNews);
	    			}	    						
	        }	        
	    } catch (SQLException | ConnectionPoolException | DaoException e) {	    	
	    	throw new NewsDaoException(e);
	    	}
	        return newsLatestList;
	 }				

	private static final String SELECT_NEWS_LIST = "SELECT * FROM news ORDER BY date DESC LIMIT ";
	@Override
	public List<News> getNewsList(Integer pageNumber, Integer pageSize) throws NewsDaoException {
		List<News> newsList = new ArrayList<>();
		int startSize = (pageNumber - 1) * pageSize;
		StringBuilder strBuilder = new StringBuilder(SELECT_NEWS_LIST);
		strBuilder.append(startSize);
		strBuilder.append(pageSize);		 
	        try (Connection connection = ConnectionPool.getInstance().takeConnection();
	        	PreparedStatement ps = connection.prepareStatement(strBuilder.toString())) {
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {	                	
	    				News news = new News.Builder()
	    						.withId(rs.getInt(NewsParameterName.JSP_ID_NEWS))
	                            .withTitle(rs.getString(NewsParameterName.JSP_TITLE_NEWS))
	                            .withBrief(rs.getString(NewsParameterName.JSP_BRIEF_NEWS))
	                            .withContent(rs.getString(NewsParameterName.JSP_CONTENT_NEWS))
	                            .withDate(LocalDate.parse(rs.getString(NewsParameterName.JSP_DATE_NEWS)))
	                            .withAuthor(userDao.findById(rs.getInt(NewsParameterName.JSP_ID_REPORTER)))
	                            .build();
	    				newsList.add(news);
	    			}	    						
	        }	        
	    } catch (SQLException | ConnectionPoolException | DaoException e) {	    	
	    	throw new NewsDaoException(e);
	    	}
	        return newsList;
	 }				
	
	private static final String SELECT_NEWS_BY_ID = "SELECT * FROM news WHERE id = ?";
	@Override
	public News fetchById(Integer idNews) throws NewsDaoException {
		News news = null;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
	        PreparedStatement ps = connection.prepareStatement(SELECT_NEWS_BY_ID)) {
			ps.setInt(1, idNews);
			try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {                	
                	news = new News.Builder()
    						.withId(rs.getInt(NewsParameterName.JSP_ID_NEWS))
                            .withTitle(rs.getString(NewsParameterName.JSP_TITLE_NEWS))
                            .withBrief(rs.getString(NewsParameterName.JSP_BRIEF_NEWS))
                            .withContent(rs.getString(NewsParameterName.JSP_CONTENT_NEWS))
                            .withDate(LocalDate.parse(rs.getString(NewsParameterName.JSP_DATE_NEWS)))
                            .withAuthor(userDao.findById(rs.getInt(NewsParameterName.JSP_ID_REPORTER)))
                            .build();
    				}
                }
			} catch (SQLException | ConnectionPoolException | DaoException e) {				
				throw new NewsDaoException(e);
			}
			return news;
	}
	
	private static final String INSERT_NEWS = "INSERT INTO news(title, brief, content, date, reporter) VALUES (?, ?, ?, ?, ?)";
	@Override
	public int addNews(News news) throws NewsDaoException {
		int row = 0;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		    PreparedStatement ps = connection.prepareStatement(INSERT_NEWS)) {
			ps.setString(1, news.getTitle());
            ps.setString(2, news.getBrief());
            ps.setString(3, news.getContent());            
            ps.setString(4, LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
            ps.setInt(5, news.getAuthor().getId());          
            row = ps.executeUpdate();
            if (row == 0) {
				throw new NewsDaoException(MESSAGE_EXCEPTION);
			}            
				} catch (SQLException | ConnectionPoolException e) {					
					throw new NewsDaoException(e);
				}
				return row;
		}
	
	private static final String UPDATE_NEWS = "UPDATE news SET title = ?, brief = ?, content = ?, date = ?, reporter = ? WHERE id = ?";
	@Override
	public boolean updateNews(News news) throws NewsDaoException {
		int row = 0;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		    PreparedStatement ps = connection.prepareStatement(UPDATE_NEWS)) {
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getContent());
			ps.setString(4, LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
            ps.setInt(5, news.getAuthor().getId());
            ps.setInt(6, news.getId());
			row = ps.executeUpdate();
			if (row == 0) {
				throw new NewsDaoException(MESSAGE_EXCEPTION);
				}
			        return true;
			        } catch (SQLException | ConnectionPoolException e) {						
						throw new NewsDaoException(e);
					}					
	}
	
	private static final String DELETE_NEWS = "DELETE FROM news WHERE id IN (?)";
	@Override
	public boolean deleteNewses(String[] idNewses) throws NewsDaoException {
		int row = 0;		
		try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
			try {
				connection.setAutoCommit(false);
		        PreparedStatement ps = connection.prepareStatement(DELETE_NEWS);
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
	
	private static final String SELECT_NEWS_TOTAL_RECORD = "SELECT COUNT(*) AS total FROM news";
	public static int getTotalRecord() throws NewsDaoException {
		int total = 0;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		        PreparedStatement ps = connection.prepareStatement(SELECT_NEWS_TOTAL_RECORD)) {
				try (ResultSet rs = ps.executeQuery()) {
					if(rs.next()) {
	                	total = rs.getInt("total");
	                	}
					}
				} catch (SQLException | ConnectionPoolException e) {
					throw new NewsDaoException(e);
					}
		return total;
	}
}