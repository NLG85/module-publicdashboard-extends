package fr.paris.lutece.plugins.publicdashboard.modules.participation.impl;

import java.util.HashMap;
import java.util.Map;

import fr.paris.lutece.plugins.dashboard.service.PublicUserProfile;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class MyPublicUserInfoProfile extends PublicUserProfile {

	private MyPublicUserInfoProfile(UserProfileBuilder builder) {
		super(builder);
	}
	
	// builder
    public static class UserProfileBuilder extends PublicUserProfile.UserProfileBuilder {
    	public UserProfileBuilder( String userid ) {
    		super( userid);
    	}
    	
    	@Override
    	public UserProfileBuilder withUserInfos( ) 
    	{
    		_mapUserInfos = searchName( _idUser );
    		return this;
    	}
    }
    
    
    	
	// mock
	private static Map<String,String> searchName( String key ) {
		Map<String,String> map = new HashMap<>();
		
		if ( key.equals("1") ) map.put( "name", "toto1" );
		if ( key.equals("2") ) map.put( "name","titi2");
		
		//CommentExtendableResourceService _resourceCommentExtenderService = SpringContextService.getBean( CommentExtendableResourceService. );
		
		
		return map;
		
	}

}
