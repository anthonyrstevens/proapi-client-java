import com.whitepages.proapi.data.association.BusinessAssociation;
import com.whitepages.proapi.data.association.LocationAssociation;
import com.whitepages.proapi.data.association.PersonAssociation;
import com.whitepages.proapi.data.association.PhoneAssociation;
import com.whitepages.proapi.data.entity.Business;
import com.whitepages.proapi.data.entity.Entity;
import com.whitepages.proapi.data.entity.Location;
import com.whitepages.proapi.data.entity.Person;
import com.whitepages.proapi.data.entity.Phone;
import com.whitepages.proapi.data.entity.Phone.Reputation;
import com.whitepages.proapi.data.entity.Phone.ReputationDetails;
import com.whitepages.proapi.data.util.TimePeriod;

import java.util.List;

public class ExampleUtils {
    private static final int _INDENT = 4;

    public static String getAPIKey( String[] args ) {
        if ( 1 != args.length ) {
            System.err.println( "Usage {class} {api_key}" );
            System.exit( 1 );
        }

        return args[ 0 ];
    }


    public static void dumpPhone( Phone phone, int depth ) {
        dumpPhone( phone, depth, 0 );
    }

    public static void dumpPerson( Person person, int depth ) {
        dumpPerson( person, depth, 0 );
    }

    public static void dumpLocation( Location location, int depth ) {
        dumpLocation( location, depth, 0 );
    }

    public static void dumpBusiness( Business business, int depth ) {
        dumpBusiness( business, depth, 0 );
    }

    private static void dumpPhone( Phone phone, int depth, int indent ) {
        printName( phone, indent );
        simpleLine( indent, "Carrier:                     %s", phone.getCarrier()            );
        simpleLine( indent, "Country Calling Code:        %s", phone.getCountryCallingCode() );
        simpleLine( indent, "Do Not Call:                 %s", phone.getDoNotCall()          );
        simpleLine( indent, "LineType:                    %s", phone.getLineType()           );
        simpleLine( indent, "PhoneNumber:                 %s", phone.getPhoneNumber()        );
        simpleLine( indent, "Is Prepaid:                  %s", phone.getPrepaid()            );
        simpleLine( indent, "Is Valid:                    %s", phone.getValid()              );
        simpleLine( indent, "Is Connected:                %s", phone.getConnected()          );
        dumpReputation(phone.getReputation(), depth, indent + _INDENT);
        dumpBaseEntity( phone, depth, indent );
    }

    private static void dumpReputation( Reputation reputation, int depth, int indent ) {
    
    	simpleLine(indent, "Reputation:");
    	if (reputation != null) {
	        simpleLine( indent, "Level:                   %s", reputation.getLevel()       );
	        simpleLine( indent, "Volume Score:            %s", reputation.getVolumeScore() );
	        simpleLine( indent, "Report Count:            %s", reputation.getReportCount() );
	        dumpReputationDetails(reputation.getDetails(), depth, indent + _INDENT);
    	} else {
    		simpleLine(indent, "null");
    	}
    }
    
    private static void dumpReputationDetails( List<ReputationDetails> details, int depth, int indent ) {
        
    	simpleLine(indent, "Details:");
    	if (details != null) {
    		for (ReputationDetails detail: details) {
    			simpleLine( indent, "[");
    	        simpleLine( indent + _INDENT, "Score:           %s", detail.getScore() );
    	        simpleLine( indent + _INDENT, "Type:            %s", detail.getType() );
    	        simpleLine( indent + _INDENT, "Category:        %s", detail.getCategory() );
    	        simpleLine( indent, "]");
    		}
	        
    	} else {
    		simpleLine(indent, "null");
    	}
    }

    private static void dumpPerson( Person person, int depth, int indent ) {
        printName( person, indent );
        
        simpleLine( indent, "Names:                       %s", person.getNames()                 );
        simpleLine( indent, "Age Range:                   %s", person.getAgeRange()              );
        simpleLine( indent, "Gender:                      %s", person.getGender()                );

        dumpBaseEntity( person, depth, indent );
    }

    private static void dumpLocation( Location location, int depth, int indent ) {
        printName( location, indent );
        simpleLine( indent, "Address Type:                %s", location.getAddressType()             );
        simpleLine( indent, "City:                        %s", location.getCity()                    );
        simpleLine( indent, "CountryCode:                 %s", location.getCountryCode()             );
        simpleLine( indent, "Deliverable:                 %s", location.getDeliverable()             );
        simpleLine( indent, "Delivery Point:              %s", location.getDeliveryPoint()           );
        simpleLine( indent, "Latitude/Longitude:          %s", stringify( location.getLatLong() )    );
        simpleLine( indent, "Not Receiving Mail Reason:   %s", location.getNotReceivingMailReason()  );
        simpleLine( indent, "Postal Code:                 %s", location.getPostalCode()              );
        simpleLine( indent, "Receiving Mail:              %s", location.getReceivingMail()           );
        simpleLine( indent, "Standard Address Line1:      %s", location.getStandardAddressLine1()    );
        simpleLine( indent, "Standard Address Line2:      %s", location.getStandardAddressLine2()    );
        simpleLine( indent, "State Code:                  %s", location.getStateCode()               );
        simpleLine( indent, "Usage:                       %s", location.getUsage()                   );
        simpleLine( indent, "ValidFor:                    %s", stringify( location.getValidFor() )   );
        simpleLine( indent, "Zip4:                        %s", location.getZip4()                    );

        dumpBaseEntity( location, depth, indent );
    }

    private static void dumpBusiness( Business business, int depth, int indent ) {
        printName( business, indent );

        dumpBaseEntity( business, depth, indent );
    }


    private static void printName( Entity entity, int indent ) {
        simpleLine( indent, "Name:                        %s", entity.getName() );
    }

    private static void dumpBaseEntity( Entity entity, int depth, int indent ) {
        if ( 1 < depth ) {
            List< BusinessAssociation > businesses = entity.getBusinessAssociations();
            if ( null != businesses && !businesses.isEmpty() ) {
                simpleLine( indent, "Businesses:" );
                for ( BusinessAssociation business : businesses ) {
                    dumpBusinessAssociation( business, depth - 1, indent + _INDENT );
                    System.out.println();
                }
            }

            List< LocationAssociation > locations  = entity.getLocationAssociations();
            if ( null != locations && !locations.isEmpty() ) {
                simpleLine( indent, "Locations:" );
                for ( LocationAssociation location : locations ) {
                    dumpLocationAssociation( location, depth - 1, indent + _INDENT );
                    System.out.println();
                }
            }

            List< PersonAssociation > people       = entity.getPersonAssociations();
            if ( null != people && !people.isEmpty() ) {
                simpleLine( indent, "People:" );
                for ( PersonAssociation person : people ) {
                    dumpPersonAssociation( person, depth - 1, indent + _INDENT );
                    System.out.println();
                }
            }

            List< PhoneAssociation > phones        = entity.getPhoneAssociations();
            if ( null != phones && !phones.isEmpty() ) {
                simpleLine( indent, "Phones:" );
                for ( PhoneAssociation phone : phones ) {
                    dumpPhoneAssociation( phone, depth - 1, indent + _INDENT );
                    System.out.println();
                }
            }

        }
    }


    private static void dumpBusinessAssociation( BusinessAssociation association, int depth, int indent ) {
        dumpBusiness( association.getBusiness(), depth, indent );
    }

    private static void dumpLocationAssociation( LocationAssociation association, int depth, int indent ) {
        simpleLine( indent, "Location:" );
        dumpLocation( association.getLocation(), depth, indent + _INDENT );
    }

    private static void dumpPersonAssociation( PersonAssociation association, int depth, int indent ) {
        dumpPerson( association.getPerson(), depth, indent );
    }

    private static void dumpPhoneAssociation( PhoneAssociation association, int depth, int indent ) {
        simpleLine( indent, "Phone:" );
        dumpPhone( association.getPhone(), depth, indent + _INDENT );
        
    }

    private static void bestLocation( Location location, int depth, int indent ) {
        if ( depth > 1 && null != location )
            simpleLine( indent, "Best Location:               %s", location.getName() );
    }

    private static Integer getLevel( Phone phone ) {
        Phone.Reputation reputation = phone.getReputation();

        return null == reputation ? null : reputation.getLevel();
    }

    private static Integer getVolumeScore( Phone phone ) {
        Phone.Reputation reputation = phone.getReputation();

        return null == reputation ? null : reputation.getVolumeScore();
    }

    private static Integer getReportCount( Phone phone ) {
        Phone.Reputation reputation = phone.getReputation();

        return null == reputation ? null : reputation.getReportCount();
    }

    private static Integer getReputationDetailsCount( Phone phone ) {
        Phone.Reputation reputation = phone.getReputation();

        List<ReputationDetails> details = reputation.getDetails();
        
        return null == details ? null : details.size();
    }

    private static void simpleLine( int indent, String format, Object value ) {
        indent( indent );
        System.out.format( format, value );
        System.out.println();
    }

    private static void simpleLine( int indent, String format ) {
        simpleLine( indent, format, null );
    }

    private static void indent( int indent ) {
        for ( int i = 0; i < indent; i++ )
            System.out.print( " " );
    }

    private static String stringify( Location.LatLong latLong ) {
        if ( null == latLong )
            return "null";

        return latLong.toString();
    }

    private static String stringify( TimePeriod period ) {
        if ( null == period )
            return "null";

        return period.toString();
    }
}
