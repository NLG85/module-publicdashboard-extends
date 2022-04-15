package fr.paris.lutece.plugins.publicdashboard.modules.participation.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import fr.paris.lutece.plugins.dashboard.service.Action;
import fr.paris.lutece.plugins.dashboard.service.Counter;
import fr.paris.lutece.plugins.dashboard.service.PublicUserProfile;
import fr.paris.lutece.plugins.extend.business.extender.history.ResourceExtenderHistory;
import fr.paris.lutece.plugins.extend.business.extender.history.ResourceExtenderHistoryFilter;
import fr.paris.lutece.plugins.extend.modules.rating.business.Rating;
import fr.paris.lutece.plugins.extend.modules.rating.service.RatingService;
import fr.paris.lutece.plugins.extend.modules.rating.service.facade.RatingFacadeFactory;
import fr.paris.lutece.plugins.extend.service.extender.history.IResourceExtenderHistoryService;
import fr.paris.lutece.plugins.extend.service.extender.history.ResourceExtenderHistoryService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class MyPublicProjectCounterProfile extends PublicUserProfile {
	
	/*
	@Inject
    @Named( ResourceExtenderHistoryService.BEAN_SERVICE )
    private IResourceExtenderHistoryService _resourceExtenderHistoryService;
	*/
	
	private MyPublicProjectCounterProfile(UserProfileBuilder builder) {
		super(builder);			
	}
	
	// builder
    public static class UserProfileBuilder extends PublicUserProfile.UserProfileBuilder {
    	public UserProfileBuilder( String userid  ) {
    		super( userid);
    	}
    	
    	@Override
		public UserProfileBuilder withCounter( String guid ) 
		{
			_listCounters = searchProjectCounter(guid);
			return this;
		}
		
    }
    
	// mock
	private static List<Counter> searchProjectCounter( String guid ) {
		
		List<Counter> listCounter = new ArrayList<>();
		
		/*
		IRatingService rService = SpringContextService.getBean( RatingService.BEAN_SERVICE);
		Rating lstRatings = rService.findByResource(key, type);
		
		listCounter.add( new Counter( lstRatings.getExtendableResourceType(), String.valueOf(lstRatings.getScoreNegativesVotes( ) ), lstRatings.getScorePositifsVotes() ) );
		*/
		
		//RatingFacadeFactory.getInfoRating(key, type);
		
		/*
		if ( key.equals("1") ) {
			listCounter.add( new Counter( "Nombre", "commentaire", 1 ) );
			listCounter.add( new Counter( "Nombre", "like", 2 ) );
		}
		
		if ( key.equals("2") ) {
			listCounter.add( new Counter( "Nombre", "commentaire", 1 ) );
			listCounter.add( new Counter( "Nombre", "like", 2 ) );
			listCounter.add( new Counter( "Nombre", "follower", 3 ) );
		}
		*/
		
		
		
		IResourceExtenderHistoryService _resourceExtenderHistoryService = SpringContextService.getBean( ResourceExtenderHistoryService.BEAN_SERVICE );
		
		Map<String, Object> model = new HashMap< >( );
        
        ResourceExtenderHistoryFilter filter = new ResourceExtenderHistoryFilter( );
        filter.setUserGuid( guid );

        Map<String, Integer> mapExtendStatistics = new HashMap< >( );
        
        for ( ResourceExtenderHistory resourceExtenderHistory :  _resourceExtenderHistoryService.findByFilter( filter ) )
        {
        	Integer value = mapExtendStatistics.get( resourceExtenderHistory.getExtenderType( ) );
        	
        	if ( value != null && value > 0 )
        	{
        		mapExtendStatistics.put( resourceExtenderHistory.getExtenderType( ), value + 1 );
        	}
        	else 
        	{
        		mapExtendStatistics.put( resourceExtenderHistory.getExtenderType( ), 1 );
        	}
        }
        
        for ( Map.Entry<String, Integer> entry : mapExtendStatistics.entrySet( ) ) 
		{

        	listCounter.add( new Counter( "Nombre ", entry.getKey(), entry.getValue( ) ) );
	    }
		
		return listCounter;
		
	}

}
