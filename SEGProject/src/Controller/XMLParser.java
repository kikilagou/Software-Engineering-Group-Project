package Controller;

import Model.Airport;
import Model.Obstacle;
import Model.Runway;

import java.awt.geom.Arc2D;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by asherfischbaum on 18/02/2017.
 *
 *
 * needs some of this bug spray! it's the only thing that can save us
 *                  ;)
 *https://www.amazon.co.uk/d/Bed-Bug-Flea-Mite-Control/Zero-Bed-Bug-Killer-Spray/B003ASRLII/ref=sr_1_6?ie=UTF8&qid=1489258505&sr=8-6&keywords=bug+spray
 *
 *
 */
public class XMLParser
{
    private File xmlFile;

    // Model.Runway XML Tags
    private final String RUNWAY_TAG = "<Runway>";
    private final String ID_TAG = "<ID>";
    private final String HEADING_TAG = "<Heading>";
    private final String CHARACTER_TAG = "<Character>";
    private final String TORA_TAG = "<TORA>";
    private final String TODA_TAG = "<TODA>";
    private final String ASDA_TAG = "<ASDA>";
    private final String LDA_TAG = "<LDA>";
    private final String REMARKS_TAG = "<Remarks>";
    private final String STOPWAY_TAG = "<Stopway>";
    private final String CLEARWAYW_TAG = "<ClearwayW>";
    private final String CLEARWAYL_TAG = "<ClearwayL>";
    private final String LENGTH_TAG = "<Length>";
    private final String THRESHDISP_TAG = "<ThreshDisp>";
    private final String WIDTH_TAG = "<Width>";
    // Obstacle XML Tags
    private final String OBSTACLE_TAG = "<Obstacle>";
    private final String IDENTIFIER_TAG = "<Identifier>";
    private final String RUNWAYPOSITION_TAG = "<RunwayPosition>";
    private final String OWIDTH_TAG = "<OWidth>";
    private final String DEPTH_TAG = "<Depth>";
    private final String HEIGHT_TAG = "<Height>";
    private final String DTR_TAG = "<DTR>";
    private final String DFT_TAG = "<DFT>";
    private final String DTC_TAG = "<DTC>";
    private final String Description_tag = "<Description>";


    /**
     * sets the xml file
     * @param xmlFile the xml file
     */

    public XMLParser(File xmlFile)
    {
        this.xmlFile = xmlFile;
    }

    /* readFile()
        // this method will read and parse the XML file
        while not at end of file
            read next line
            check tag
            if ""</Model.Runway>" or "</Model.Obstacle>"
                pass
            else
                if runway tag then runway = true, obstacle = false
                else if obstacle tag then obstacle = true, runway = false
                if runway == true
                    String id, heading, char, tora, toda, asda, lda, remarks -> all null
                    if matching runway xml tag, set corresponding string to value
                    if all not null (except char) then create + return RecordRunway obj
                    else throw exception/error
               else if obstacle == true
                    String id, runwayPos, width, depth, height -> all null
                    if matching obstacle xml tags set corresponding string to value
                    if all not null then create + return RecordObstacle obj
           //TODO error checking and XMLresults class
     */

    /**
     * reads the inputed fule
     * @return an airport object
     * @throws FileNotFoundException if the specified file does not exist
     */
    public Airport readFile() throws FileNotFoundException
    {

        Runway runway1 = null, runway2 = null;
        Obstacle runwayObstacle = null;

        BufferedReader reader = null;
        // for airportStuff
        boolean airportStart = false;
        Airport airport = null;

        // for runway
        String id = null, heading = null, character = null, tora = null,
                toda = null, asda = null, lda = null, remarks = null, stopway = null, clearwayW = null,
                clearwayL = null, runwayL=null, runwayW=null, threshDisp=null;


        // for obstacles
        String identifier = null, runwayPos = null, width = null, depth = null, height = null, DTR = null, DFT = null, DTC = null, description = null;

        String line = null;
        try
        {
            reader = new BufferedReader(new FileReader(xmlFile));
            boolean isRunway = false, isObstacle = false;


            while((line = reader.readLine()) != null)
            {

                if (airportStart) {
                	
                    if (line.contains("</Obstacle>") || line.contains("</Runway1>") || line.contains("</Runway2>") ||
                            line.contains("</Airport>") || line.contains("</Runway>")) {
                        if (line.contains("</Runway1>")) {
                            isRunway = false;
                            runway1 = new Runway(id, remarks, Integer.parseInt(heading), character.charAt(0),
                                    Double.parseDouble(runwayL), Double.parseDouble(runwayW), Double.parseDouble(tora),
                                    Double.parseDouble(toda), Double.parseDouble(asda), Double.parseDouble(lda),
                                    Double.parseDouble(threshDisp), Double.parseDouble(stopway),
                                    Double.parseDouble(clearwayW), Double.parseDouble(clearwayL));
                            heading=null;
                            id = null;
                            character = null;
                            remarks = null;
                            runwayL=null;
                            runwayW=null;
                            tora=null;
                            toda=null;
                            asda=null;
                            lda=null;
                            threshDisp=null;
                            stopway=null;
                            clearwayL=null;
                            clearwayW=null;
                            if (runwayObstacle != null) {
                                runway1.setObstacle(runwayObstacle);
                                runwayObstacle = null;
                            }
                            airport.addRunway(runway1); // // TODO: check if remarks is the same as the displacement threshold.
                        } else if (line.contains("</Runway2>")) {
                            isRunway = false;
                            runway2 = new Runway(id, remarks, Integer.parseInt(heading), character.charAt(0),
                                    Double.parseDouble(runwayL), Double.parseDouble(runwayW), Double.parseDouble(tora),
                                    Double.parseDouble(toda), Double.parseDouble(asda), Double.parseDouble(lda),
                                    Double.parseDouble(threshDisp), Double.parseDouble(stopway),
                                    Double.parseDouble(clearwayW), Double.parseDouble(clearwayL));
                            heading=null;
                            id = null;
                            character = null;
                            remarks = null;
                            runwayL=null;
                            runwayW=null;
                            tora=null;
                            toda=null;
                            asda=null;
                            lda=null;
                            threshDisp=null;
                            stopway=null;
                            clearwayL=null;
                            clearwayW=null;
                            if (runwayObstacle != null) {
                                runway2.setObstacle(runwayObstacle);
                                runwayObstacle = null;
                            }
                        }else if (line.contains("</Obstacle>")){
                            isObstacle = false;
                            runwayObstacle = new Obstacle(Double.parseDouble(height), Double.parseDouble(width),
                                    Double.parseDouble(depth), Double.parseDouble(DFT), Double.parseDouble(DTC),
                                    identifier, description); //todo check if runway position is the same as description

                            height = null;
                            width = null;
                            depth= null;
                            DFT = null;
                            DTC=null;
                            identifier = null;
                            description = null;
                        } else if (line.contains("</Runway>")){
                        	isRunway = false;
                            if (runway1 != null && runway2 != null){
                                runway1.setComplement(runway2);
                                runway2.setComplement(runway1);
                                runway1 = null;
                                runway2 = null;
                            }
                        } else {
                            airportStart = false;
                        }

                    } else {
                        if (line.contains("<Name>")){
                            airport = new Airport(getData(line));
                        }
                        if (line.contains(RUNWAY_TAG) | line.contains("<Runway1>") | line.contains("<Runway2>")) {
                            isRunway = true;
                        } else if (line.contains(OBSTACLE_TAG)) {
                            isObstacle = true;
                        }
                        if (isObstacle) {
                                if (line.contains(IDENTIFIER_TAG)) {
                                    identifier = getData(line);
                                } else if (line.contains(RUNWAYPOSITION_TAG)) {
                                    runwayPos = getData(line);
                                } else if (line.contains(WIDTH_TAG)) {
                                    width = getData(line);
                                } else if (line.contains(DEPTH_TAG)) {
                                    depth = getData(line);
                                } else if (line.contains(OWIDTH_TAG)){
                                    width = getData(line);
                                }else if (line.contains(HEIGHT_TAG)) {
                                    height = getData(line);
                                } else if (line.contains(DTR_TAG)){
                                    DTR = getData(line);
                                } else if(line.contains(DFT_TAG)) {
                                    DFT = getData(line);
                                } else if(line.contains(DTC_TAG)) {
                                	DTC = getData(line);
                                } else if(line.contains(Description_tag)) {
                                    description = getData(line);
                                }
                            }
                        if (isRunway) {
                            if (line.contains(ID_TAG)) {
                                id = getData(line);
                            } else if (line.contains(HEADING_TAG)) {
                                heading = getData(line);
                            } else if (line.contains(CHARACTER_TAG)) {
                                character = getData(line);
                            } else if (line.contains(TORA_TAG)) {
                                tora = getData(line);
                            } else if (line.contains(TODA_TAG)) {
                                toda = getData(line);
                            } else if (line.contains(ASDA_TAG)) {
                                asda = getData(line);
                            } else if (line.contains(LDA_TAG)) {
                                lda = getData(line);
                            } else if (line.contains(REMARKS_TAG)) {
                                remarks = getData(line);
                            } else if (line.contains(STOPWAY_TAG)){
                                stopway = getData(line);
                            } else if (line.contains(CLEARWAYW_TAG)){
                                clearwayW = getData(line);
                            }	else if (line.contains(CLEARWAYL_TAG)){
                                clearwayL = getData(line);
                            } else if (line.contains(LENGTH_TAG)){
                                runwayL = getData(line);
                            } else if (line.contains(WIDTH_TAG)){
                                runwayW = getData(line);
                            }else if (line.contains(THRESHDISP_TAG)){
                                threshDisp = getData(line);
                            }
                        } 
                    }


                } else {
                    if (line.contains("<Airport>")){
                        airportStart = true;
                    }
                }


            }









        } catch (IOException e) {
            System.err.println("No lines to be read");
            // // TODO ask group if this should be a popup notification
        } finally
        {
            if(reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Nothing needed
                    e.printStackTrace();
                }
            }
        }

        return airport;


    }


    /**
     * reads in the xml for a runway and creates a runway object
     * @return a runway object
     */
    public Runway readRunwayXML() {
        BufferedReader reader = null;
        boolean isRunway = false, isObstacle = false;
        String line = null;
        Runway currentRunway = null;
        // for runway
        Runway runway1=null, runway2=null;
        Obstacle runwayObstacle = null;
        String id = null, heading = null, character = null, tora = null,
                toda = null, asda = null, lda = null, remarks = null, stopway = null, clearwayW = null,
                clearwayL = null, runwayL=null, runwayW=null, threshDisp=null;


        // for obstacles
        String identifier = null, runwayPos = null, width = null, depth = null, height = null, DTR = null, DFT = null, DTC = null, description = null;

        try {
            reader = new BufferedReader(new FileReader(xmlFile));

            while ((line = reader.readLine()) != null) {
            	
                if (line.contains("</Obstacle>") || line.contains("</Runway1>") || line.contains("</Runway2>") ||
                        line.contains("</Airport>") || line.contains("</Runway>")) {
                    if (line.contains("</Runway1>")) {
                        isRunway = false;
                        runway1 = new Runway(id, remarks, Integer.parseInt(heading), character.charAt(0),
                                Double.parseDouble(runwayL), Double.parseDouble(runwayW), Double.parseDouble(tora),
                                Double.parseDouble(toda), Double.parseDouble(asda), Double.parseDouble(lda),
                                Double.parseDouble(threshDisp), Double.parseDouble(stopway),
                                Double.parseDouble(clearwayW), Double.parseDouble(clearwayL));
                        heading=null;
                        id = null;
                        character = null;
                        remarks = null;
                        runwayL=null;
                        runwayW=null;
                        tora=null;
                        toda=null;
                        asda=null;
                        lda=null;
                        threshDisp=null;
                        stopway=null;
                        clearwayL=null;
                        clearwayW=null;
                        if (runwayObstacle != null) {
                            runway1.setObstacle(runwayObstacle);
                            runwayObstacle = null;
                        }
                    } else if (line.contains("</Runway2>")) {
                        isRunway = false;
                        runway2 = new Runway(id, remarks, Integer.parseInt(heading), character.charAt(0),
                                Double.parseDouble(runwayL), Double.parseDouble(runwayW), Double.parseDouble(tora),
                                Double.parseDouble(toda), Double.parseDouble(asda), Double.parseDouble(lda),
                                Double.parseDouble(threshDisp), Double.parseDouble(stopway),
                                Double.parseDouble(clearwayW), Double.parseDouble(clearwayL));
                        heading=null;
                        id = null;
                        character = null;
                        remarks = null;
                        runwayL=null;
                        runwayW=null;
                        tora=null;
                        toda=null;
                        asda=null;
                        lda=null;
                        threshDisp=null;
                        stopway=null;
                        clearwayL=null;
                        clearwayW=null;
                        if (runwayObstacle != null) {
                            runway2.setObstacle(runwayObstacle);
                            runwayObstacle = null;
                        }
                    }else if (line.contains("</Obstacle>")){
                        isObstacle = false;
                        runwayObstacle = new Obstacle(Double.parseDouble(height), Double.parseDouble(width),
                                Double.parseDouble(depth), Double.parseDouble(DFT), Double.parseDouble(DTC),
                                identifier, description); //todo check if runway position is the same as description

                        height = null;
                        width = null;
                        depth= null;
                        DFT = null;
                        DTC=null;
                        identifier = null;
                        description = null;
                    } else if (line.contains("</Runway>")){
                        if (runway1 != null && runway2 != null){
                            runway1.setComplement(runway2);
                            runway2.setComplement(runway1);
                        }
                    }

                } else {
                    if (line.contains(RUNWAY_TAG) | line.contains("<Runway1>") | line.contains("<Runway2>")) {
                        isRunway = true;
                    } else if (line.contains(OBSTACLE_TAG)) {
                        isObstacle = true;
                    } else if (isRunway || isObstacle) {
                        if (line.contains(ID_TAG)) {
                            id = getData(line);
                        } else if (line.contains(HEADING_TAG)) {
                            heading = getData(line);
                        } else if (line.contains(CHARACTER_TAG)) {
                            character = getData(line);
                        } else if (line.contains(TORA_TAG)) {
                            tora = getData(line);
                        } else if (line.contains(TODA_TAG)) {
                            toda = getData(line);
                        } else if (line.contains(ASDA_TAG)) {
                            asda = getData(line);
                        } else if (line.contains(LDA_TAG)) {
                            lda = getData(line);
                        } else if (line.contains(REMARKS_TAG)) {
                            remarks = getData(line);
                        } else if (line.contains(STOPWAY_TAG)){
                            stopway = getData(line);
                        } else if (line.contains(CLEARWAYW_TAG)){
                            clearwayW = getData(line);
                        }	else if (line.contains(CLEARWAYL_TAG)){
                            clearwayL = getData(line);
                        } else if (line.contains(LENGTH_TAG)){
                            runwayL = getData(line);
                        } else if (line.contains(WIDTH_TAG)){
                            runwayW = getData(line);
                        }else if (line.contains(THRESHDISP_TAG)){
                            threshDisp = getData(line);
                        } else if (line.contains(IDENTIFIER_TAG)) {
                            identifier = getData(line);
                        } else if (line.contains(RUNWAYPOSITION_TAG)) {
                            runwayPos = getData(line);
                        } else if (line.contains(WIDTH_TAG)) {
                            width = getData(line);
                        } else if (line.contains(DEPTH_TAG)) {
                            depth = getData(line);
                        } else if (line.contains(HEIGHT_TAG)) {
                            height = getData(line);
                        } else if (line.contains(OWIDTH_TAG)) {
                            width = getData(line);
                        }else if (line.contains(DTR_TAG)) {
                            DTR = getData(line);
                        } else if (line.contains(DFT_TAG)) {
                            DFT = getData(line);
                        } else if (line.contains(DTC_TAG)) {
                            DTC = getData(line);
                        } else if (line.contains(Description_tag)) {
                            description = getData(line);
                        }
                    }
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return runway1;
    }

    /**
     * reads in the xml of an obstacle and returns its object
     * @param fileName the name of the obstacle xml file
     * @return an obstacle object
     */
    public Obstacle readObstactleXML(String fileName){
        Obstacle obstacle = null;
        // for obstacles
        String identifier = null, runwayPos = null, width = null, depth = null, height = null, DTR = null, DFT = null, DTC = null, description = null;

        BufferedReader reader = null;
        boolean isObstacle = false;

        String line = null;

        try {
            reader = new BufferedReader(new FileReader(xmlFile));

            while ((line = reader.readLine()) != null) {
                if (line.contains("</Obstacle>") || line.contains("<Obstacle>") ) {
                    if (line.contains("</Obstacle>")) {
                        isObstacle = false;
                        obstacle = new Obstacle(Double.parseDouble(height), Double.parseDouble(width),
                                Double.parseDouble(depth),identifier, description); //todo check if runway position is the same as description

                        height = null;
                        width = null;
                        depth= null;
                        DFT = null;
                        DTC=null;
                        identifier = null;
                        description = null;
                    } else if (line.contains("<Obstacle>")){
                        isObstacle = true;
                    }

                } else {
                     if (isObstacle) {
                         if (line.contains(IDENTIFIER_TAG)) {
                             identifier = getData(line);
                         } else if (line.contains(RUNWAYPOSITION_TAG)) {
                             runwayPos = getData(line);
                         } else if (line.contains(WIDTH_TAG)) {
                             width = getData(line);
                         } else if (line.contains(DEPTH_TAG)) {
                             depth = getData(line);
                         } else if (line.contains(HEIGHT_TAG)) {
                             height = getData(line);
                         } else if (line.contains(DTR_TAG)) {
                             DTR = getData(line);
                         } else if (line.contains(DFT_TAG)) {
                             DFT = getData(line);
                         } else if (line.contains(DTC_TAG)) {
                             DTC = getData(line);
                         } else if (line.contains(Description_tag)) {
                             description = getData(line);
                         }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return obstacle;
    }

    /**
     * writes out an airport to XML
     * @param airport the airport object to be written out
     * @return the filename of the airport xml
     */
    public String XMLAirportWrite(Airport airport){

        List all_runways = airport.getRunways();
        int amount_of_numbers = all_runways.size();

        String fileName = "";

        String XML = "<Airport>\n";
        XML += "\t<Name>" + airport.getAirportName() + "</Name>\n";


        /**
         * 	<Runway1>
         <ID>01</ID>
         <Remarks> LDA: 09R landing threshold is depilated by 307 meters  </Remarks>
         <Heading>09</Heading>
         <Character>R</Character>
         <Length> </Length>
         <Width></width>
         <TORA>3660</TORA> *Take-Off Run Available*
         <TODA>3660</TODA> *Take-Off Distance Available*
         <ASDA>3660</ASDA> *Accelerate-Stop Distance Available*
         <LDA>3353</LDA> *Landing distance available*
         <ThreshDisp>100</ThreshDisp>
         <Stopway> 60 </Stopway>
         <ClearwayW>30</ClearwayW>
         <ClearwayL>50</ClearwayL>
         </Runway1>

         */
        if (airport.getRunways().size() != 0) {

            for (int i = 0; i < all_runways.size(); i++) {
                Runway runway = (Runway) all_runways.get(i);
                Obstacle runway_obstacle = runway.getObstacle();
                XML += "\t<Runway>\n";
                XML += "\t\t<Runway1>\n";
                XML += "\t\t\t<ID>" + runway.getId() + "</ID>\n";
                XML += "\t\t\t<Remarks>" + runway.getThresholdDisplacement() + "</Remarks>\n";
                XML += "\t\t\t<Heading>" + runway.getHeading() + "</Heading>\n";
                XML += "\t\t\t<Character>" + runway.getDirectionChar() + "</Character>\n";
                XML += "\t\t\t<Length>" + runway.getRunwayLength() + "</Length>\n";
                XML += "\t\t\t<Width>" + runway.getRunwayWidth() + "</Width>\n";
                XML += "\t\t\t<TORA>" + runway.getTora() + "</TORA>\n";
                XML += "\t\t\t<TODA>" + runway.getToda() + "</TODA>\n";
                XML += "\t\t\t<ASDA>" + runway.getAsda() + "</ASDA>\n";
                XML += "\t\t\t<LDA>" + runway.getLda() + "</LDA>\n";
                XML += "\t\t\t<ThreshDisp>" + runway.getThresholdDisplacement() + "</ThreshDisp>\n";
                XML += "\t\t\t<Stopway>" + runway.getStopwayLength() + "</Stopway>\n";
                XML += "\t\t\t<ClearwayW>" + runway.getClearwayWidth() + "</ClearwayW>\n";
                XML += "\t\t\t<ClearwayL>" + runway.getClearwayLength() + "</ClearwayL>\n";
                if (runway_obstacle == null) {

                } else {
                    XML += "\t<Obstacle>\n";
                    XML += "\t\t<Identifier>" + runway_obstacle.getId() + "</Identifier>\n";
                    XML += "\t\t<RunwayPosition>" + runway_obstacle.getDescription() + "</RunwayPosition>\n";
                    XML += "\t\t<Width>" + runway_obstacle.getWidth() + "</Width>\n";
                    XML += "\t\t<Depth>" + runway_obstacle.getDepth() + "</Depth>\n";
                    XML += "\t\t<Height>" + runway_obstacle.getHeight() + "</Height>\n";
                    XML += "\t\t<DFT>" + runway_obstacle.getDistanceFromThresh() + "</DFT>\n";
                    XML += "\t\t<DTC>" + runway_obstacle.getCenterlineDist() + "</DTC>\n";
                    XML += "\t</Obstacle>\n";
                }
                XML += "\t\t</Runway1>\n";
                XML += "\t\t<Runway2>\n";
                XML += "\t\t\t<ID>" + runway.getComplement().getId() + "</ID>\n"; // HERE
                XML += "\t\t\t<Remarks>" + runway.getComplement().getThresholdDisplacement() + "</Remarks>\n";
                XML += "\t\t\t<Heading>" + runway.getComplement().getHeading() + "</Heading>\n";
                XML += "\t\t\t<Character>" + runway.getComplement().getDirectionChar() + "</Character>\n";
                XML += "\t\t\t<Length>" + runway.getComplement().getRunwayLength() + "</Length>\n";
                XML += "\t\t\t<Width>" + runway.getComplement().getRunwayWidth() + "</Width>\n";
                XML += "\t\t\t<TORA>" + runway.getComplement().getTora() + "</TORA>\n";
                XML += "\t\t\t<TODA>" + runway.getComplement().getToda() + "</TODA>\n";
                XML += "\t\t\t<ASDA>" + runway.getComplement().getAsda() + "</ASDA>\n";
                XML += "\t\t\t<LDA>" + runway.getComplement().getLda() + "</LDA>\n";
                XML += "\t\t\t<ThreshDisp>" + runway.getComplement().getThresholdDisplacement() + "</ThreshDisp>\n";
                XML += "\t\t\t<Stopway>" + runway.getComplement().getStopwayLength() + "</Stopway>\n";
                XML += "\t\t\t<ClearwayW>" + runway.getComplement().getClearwayWidth() + "</ClearwayW>\n";
                XML += "\t\t\t<ClearwayL>" + runway.getComplement().getClearwayLength() + "</ClearwayL>\n";
                Obstacle obstacle = runway.getComplement().getObstacle();
                if (obstacle == null) {

                } else {
                    XML += "\t<Obstacle>\n";
                    XML += "\t\t<Identifier>" + obstacle.getId() + "</Identifier>\n";
                    XML += "\t\t<RunwayPosition>" + obstacle.getDescription() + "</RunwayPosition>\n";
                    XML += "\t\t<Width>" + obstacle.getWidth() + "</Width>\n";
                    XML += "\t\t<Depth>" + obstacle.getDepth() + "</Depth>\n";
                    XML += "\t\t<Height>" + obstacle.getHeight() + "</Height>\n";
                    XML += "\t\t<DFT>" + obstacle.getDistanceFromThresh() + "</DFT>\n";
                    XML += "\t\t<DTC>" + obstacle.getCenterlineDist() + "</DTC>\n";
                    XML += "\t</Obstacle>\n";
                }
                XML += "\t\t</Runway2>\n";
                XML += "\t</Runway>\n";
            }
        }

        XML += "</Airport>\n";

        try{
            Date date = new Date() ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss") ;
//            PrintWriter writer = new PrintWriter(dateFormat + ".txt", "UTF-8");
            PrintWriter writer = new PrintWriter(xmlFile, "UTF-8");
            writer.println(XML);
            writer.close();

//            fileName = dateFormat + ".txt";
            fileName = "sampleAirport.txt";

        } catch (IOException e) {
            // do something
        }

        return fileName;
    }

    /**
     * writes out an obstacle to xml
     * @param obstacle the obstacle object to be written out
     * @return the name of the obstacle xml file
     */
    public String XMLObstacleWrite(Obstacle obstacle){

        String fileName = "SampleObstacles.txt";

        String XML = "<Obstacle>\n";
        XML += "\t<Identifier>" + obstacle.getId() +"</Identifier>\n";
        XML += "\t<Description>" + obstacle.getDescription() + "</Description>\n";
        XML += "\t<Width>" + obstacle.getWidth() + "</Width>\n";
        XML += "\t<Depth>" + obstacle.getDepth() + "</Depth>\n";
        XML += "\t<Height>"+ obstacle.getHeight() + "</Height>\n";
        XML += "\t\t<DFT>" + obstacle.getDistanceFromThresh() + "</DFT>\n";
        XML += "\t\t<DTC>" + obstacle.getCenterlineDist() + "</DTC>\n";
        XML += "</Obstacle>\n";

        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss") ;
//            PrintWriter writer = new PrintWriter("Obstacle-"+obstacle.getId() + "-" + dateFormat + ".txt", "UTF-8");
            PrintWriter writer = new PrintWriter(xmlFile, "UTF-8");
            writer.println(XML);
            writer.close();

//            fileName = "Obstacle-"+obstacle.getId() + "-" + dateFormat + ".txt";
            fileName = "SampleObstacles.txt";
        } catch (IOException e) {
            // do something
        }

        return fileName;

    }

    /**
     * writes the runway to xml
     * @param runway the runway obstacle
     * @return the name of the xml file of the runway
     */
    public String XMLRunwayWrite(Runway runway){

        String fileName = "";

        String XML = "<Runway>\n";
        XML += "<Runway1>\n";
        XML += "\t<ID>"+ runway.getId() + "</ID>\n";
        XML += "\t<Heading>" + runway.getHeading() + "</Heading>\n";
        XML += "\t<Character>" + runway.getDirectionChar() + "</Character>\n";
        XML += "\t<TORA>" + runway.getTora() + "</TORA>\n";
        XML += "\t<TODA>" + runway.getToda() + "</TODA>\n";
        XML += "\t<ASDA>" + runway.getAsda() + "</ASDA>\n";
        XML += "\t<LDA>" + runway.getLda() + "</LDA>\n";
        XML += "\t<Remarks>" + runway.getRemarks() + "</Remarks>\n";
        XML += "\t\t\t<ThreshDisp>" + runway.getThresholdDisplacement() + "</ThreshDisp>\n";
        XML += "\t<Stopway>" + runway.getStopwayLength() +"</Stopway>\n";
        XML += "\t<ClearwayW>" + runway.getClearwayWidth() + "</ClearwayW>\n";
        XML += "\t<ClearwayL>" + runway.getClearwayLength() + "</ClearwayL>\n";
        XML += "\t<Length>" + runway.getRunwayLength() + "</Length>\n";
        XML += "\t<Width>" + runway.getRunwayWidth() + "</Width>\n";
        XML += "</Runway1>\n";
        
        if(runway.getComplement() !=null)
        {
        	XML += "<Runway2>\n";
            XML += "\t<ID>"+ runway.getComplement().getId() + "</ID>\n";
            XML += "\t<Heading>" + runway.getComplement().getHeading() + "</Heading>\n";
            XML += "\t<Character>" + runway.getComplement().getDirectionChar() + "</Character>\n";
            XML += "\t<TORA>" + runway.getComplement().getTora() + "</TORA>\n";
            XML += "\t<TODA>" + runway.getComplement().getToda() + "</TODA>\n";
            XML += "\t<ASDA>" + runway.getComplement().getAsda() + "</ASDA>\n";
            XML += "\t<LDA>" + runway.getComplement().getLda() + "</LDA>\n";
            XML += "\t<Remarks>" + runway.getComplement().getRemarks() + "</Remarks>\n";
            XML += "\t\t\t<ThreshDisp>" + runway.getComplement().getThresholdDisplacement() + "</ThreshDisp>\n";
            XML += "\t<Stopway>" + runway.getComplement().getStopwayLength() +"</Stopway>\n";
            XML += "\t<ClearwayW>" + runway.getComplement().getClearwayWidth() + "</ClearwayW>\n";
            XML += "\t<ClearwayL>" + runway.getComplement().getClearwayLength() + "</ClearwayL>\n";
            XML += "\t<Length>" + runway.getComplement().getRunwayLength() + "</Length>\n";
            XML += "\t<Width>" + runway.getComplement().getRunwayWidth() + "</Width>\n";
            XML += "</Runway2>\n";
        }
        XML += "</Runway>\n";

        Obstacle runway_obstacle = runway.getObstacle();

        if (runway_obstacle == null){

        } else {
            XML += "\t<Obstacle>\n";
            XML += "\t<Identifier>" + runway_obstacle.getId() +"</Identifier>\n";
            XML += "\t<RunwayPosition>" + runway_obstacle.getDescription() + "</RunwayPosition>\n";
            XML += "\t<Width>" + runway_obstacle.getWidth() + "</Width>\n";
            XML += "\t<Depth>" + runway_obstacle.getDepth() + "</Depth>\n";
            XML += "\t<Height>"+ runway_obstacle.getHeight() + "</Height>";
            XML += "\t<DFT>" + runway_obstacle.getDistanceFromThresh() + "</DFT>\n";
            XML += "\t\t<DTC>" + runway_obstacle.getCenterlineDist() + "</DTC>\n";
            XML += "</Obstacle>\n";
        }

        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss") ;
            PrintWriter writer = new PrintWriter(xmlFile, "UTF-8");
            writer.println(XML);
            writer.close();

            fileName = "Runway-"+runway.getId() + "-" + dateFormat + ".txt";

        } catch (IOException e) {
            // do something
        }

        return fileName;
    }

    /**
     * gets data from a particular xml string
     * @param data the xml string
     * @return the data held in that string
     */
    private String getData(String data)
    {
        /*
            split data string at ">" -> in var arr1
            split arr1 at "<" -> in arr2
            return arr2[0]
         */

        String[] arr1 = data.split(">");
        String[] arr2 = arr1[1].split("<");
        return arr2[0];
    }
}