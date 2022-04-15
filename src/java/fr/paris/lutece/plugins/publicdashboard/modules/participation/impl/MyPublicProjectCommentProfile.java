package fr.paris.lutece.plugins.publicdashboard.modules.participation.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import fr.paris.lutece.plugins.dashboard.service.Action;
import fr.paris.lutece.plugins.dashboard.service.PublicUserProfile;
import fr.paris.lutece.plugins.extend.business.extender.history.ResourceExtenderHistory;
import fr.paris.lutece.plugins.extend.business.extender.history.ResourceExtenderHistoryFilter;
import fr.paris.lutece.plugins.extend.modules.comment.business.Comment;
import fr.paris.lutece.plugins.extend.modules.comment.business.CommentFilter;
import fr.paris.lutece.plugins.extend.modules.comment.service.CommentService;
import fr.paris.lutece.plugins.extend.modules.comment.service.ICommentService;
import fr.paris.lutece.plugins.extend.modules.rating.business.Rating;
import fr.paris.lutece.plugins.extend.modules.rating.service.RatingService;
import fr.paris.lutece.plugins.extend.modules.rating.service.facade.RatingFacadeFactory;
import fr.paris.lutece.plugins.extend.service.extender.history.IResourceExtenderHistoryService;
import fr.paris.lutece.plugins.extend.service.extender.history.ResourceExtenderHistoryService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class MyPublicProjectCommentProfile extends PublicUserProfile {
	
	/*
	@Inject
    @Named( ResourceExtenderHistoryService.BEAN_SERVICE )
    private IResourceExtenderHistoryService _resourceExtenderHistoryService;
	*/
	
	private MyPublicProjectCommentProfile(UserProfileBuilder builder) {
		super(builder);			
	}
	
	// builder
    public static class UserProfileBuilder extends PublicUserProfile.UserProfileBuilder {
    	public UserProfileBuilder( String userid  ) {
    		super( userid);
    	}
    	
    	@Override
		public UserProfileBuilder withActions( String guid ) 
		{
			_listActions = searchParticipationComment(guid);
			return this;
		}
		
    }
    
	// mock
	private static List<Action> searchParticipationComment( String guid ) {
		
		List<Action> listComment = new ArrayList<>();	
		
		ICommentService commService = SpringContextService.getBean( CommentService.BEAN_SERVICE );
		
		List<Comment> lstComment = commService.findCommentsByLuteceUser(guid, 0, 0);
		for (Comment comm : lstComment)
		{
			listComment.add( new Action( comm.getDateComment( ).toString( ), comm.getComment( ) )  );
		}
		
		//CommentFilter commFilter = new CommentFilter();
		//commFilter.setLuteceUserName(guid);
		
		return listComment;
		
	}

}
