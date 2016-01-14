package demo.model;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Stephen Lavery (stephen.lavery@sas.com)
 */
public class Club
{
    private Map<String,int[]> clubRanks = new HashMap<>();
    private Map<String,String> nameConversion = new HashMap<>();

    public Club()
    {
        initialiseClubs();
    }

    private void initialiseClubs()
    {
        clubRanks.put("Chelsea", new int[]{1,8,16,10,13,17,15,14,16,12,15,15,16,15,14,14,16,15,15,14,14,14});
        clubRanks.put("Man City", new int[]{2,1,1,1,1,1,1,1,1,1,1,1,1,3,1,3,3,3,3,3,3,3});
        clubRanks.put("Arsenal", new int[]{3,19,11,9,6,4,5,4,2,2,2,2,2,4,4,2,2,2,2,1,1,1});
        clubRanks.put("Man Utd", new int[]{4,7,4,4,5,3,2,2,3,3,4,4,4,2,3,4,4,5,6,6,5,6});
        clubRanks.put("Spurs", new int[]{5,15,13,15,16,12,9,6,8,7,6,5,5,5,5,5,5,4,4,4,4,4});
        clubRanks.put("Liverpool", new int[]{6,6,3,7,10,13,9,10,10,9,8,8,9,9,6,8,9,9,8,7,8,9});
        clubRanks.put("Southampton", new int[]{7,11,17,18,10,16,10,11,9,8,8,7,7,8,10,12,12,12,12,12,13,12});
        clubRanks.put("Swansea", new int[]{8,12,6,6,4,8,7,11,11,14,12,13,14,14,15,15,17,18,16,17,17,17});
        clubRanks.put("Stoke", new int[]{9,16,14,16,18,18,18,17,14,11,14,14,12,11,12,11,11,11,11,10,10,7});
        clubRanks.put("Crystal Palace", new int[]{10,3,7,5,2,6,8,7,4,6,7,10,10,10,7,6,6,6,5,5,7,8});
        clubRanks.put("Everton", new int[]{11,9,5,7,9,7,6,5,7,9,11,9,9,7,9,9,10,10,9,11,11,11});
        clubRanks.put("West Ham", new int[]{12,4,7,11,8,5,3,3,6,4,3,6,6,6,8,7,8,8,10,8,6,5});
        clubRanks.put("West Brom", new int[]{13,20,18,20,15,14,12,15,17,13,10,12,13,12,13,13,13,13,13,13,12,13});
        clubRanks.put("Leicester", new int[]{14,2,2,2,3,2,4,8,5,5,5,3,3,1,2,1,1,1,1,2,2,2});
        clubRanks.put("Newcastle", new int[]{15,10,15,17,19,20,19,19,20,18,19,18,17,17,19,18,15,17,18,18,18,19});
        clubRanks.put("Sunderland", new int[]{16,17,20,18,20,19,20,20,19,20,18,19,19,18,17,19,19,19,19,19,19,18});
        clubRanks.put("Aston Villa", new int[]{17,5,10,14,12,15,17,18,18,19,20,20,20,20,20,20,20,20,20,20,20,20});
        clubRanks.put("Bournemouth", new int[]{18,14,19,13,11,16,14,16,15,17,17,17,18,19,18,17,14,14,14,14,16,16});
        clubRanks.put("Watford", new int[]{19,13,12,12,17,13,10,13,12,15,13,11,11,13,11,10,7,7,7,9,9,10});
        clubRanks.put("Norwich", new int[]{20,18,9,8,14,9,11,12,13,13,16,16,15,16,16,16,18,16,17,15,15,15});

        nameConversion.put("CHE", "Chelsea");
        nameConversion.put("MCI", "Man City");
        nameConversion.put("ARS", "Arsenal");
        nameConversion.put("MUN", "Man Utd");
        nameConversion.put("TOT", "Spurs");
        nameConversion.put("LIV", "Liverpool");
        nameConversion.put("SOU", "Southampton");
        nameConversion.put("SWA", "Swansea");
        nameConversion.put("STK", "Stoke");
        nameConversion.put("CRY", "Crystal Palace");
        nameConversion.put("EVE", "Everton");
        nameConversion.put("WHU", "West Ham");
        nameConversion.put("WBA", "West Brom");
        nameConversion.put("LEI", "Leicester");
        nameConversion.put("NEW", "Newcastle");
        nameConversion.put("SUN", "Sunderland");
        nameConversion.put("AVL", "Aston Villa");
        nameConversion.put("BOU", "Bournemouth");
        nameConversion.put("WAT", "Watford");
        nameConversion.put("NOR", "Norwich");


    }

    public int getRank(String teamName, int weekNo)
    {
        int[] ranks = clubRanks.get(teamName);
        try
        {
            int rank = ranks[weekNo];
            return rank;
        }
        catch (Exception ne)
        {
            System.out.println("Team Name: " + teamName + " week no; " + weekNo);
            throw ne;

        }
    }

    public String getLongName(String shortName)
    {
        String longName = nameConversion.get(shortName);
        return  longName;
    }


}
