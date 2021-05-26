import javafx.util.Pair;
import log.Log;
import property.*;
import simulation.SoSSimulationProgram;
import misc.Settings;
import verifier.SPRT;

import java.util.ArrayList;

public class main {
    private static Settings settingsInstance = Settings.getSettingsInstance();
    Log log = new Log();
    public static void main(String [] args){
        long programStartTime;                                          // 프로그램 시작 시간
        long programEndTime;                                            // 프로그램 종료 시간
        long thetaStartTime;                                            // 한 사이클 시작 시간
        long thetaEndTime;                                              // 한 사이클 종료 시간
        boolean ruuning = true;
        //runtime init args parse code START
        ArrayList<String> parsed = new ArrayList<>();

        if (args.length>0) {
            for (int i=0;i< args.length;i++){
                String arguments = args[i];
                if(arguments.equalsIgnoreCase("-a")) {
                    parsed.add(args[i]);
                    parsed.add(args[i+1]);
                    settingsInstance.setMaxAmbulance(Integer.parseInt(args[i+1]));
                } else if(arguments.equalsIgnoreCase("-ff")) {
                    parsed.add(args[i]);
                    parsed.add(args[i+1]);
                    settingsInstance.setMaxFireFighter(Integer.parseInt(args[i+1]));
                } else if(arguments.equalsIgnoreCase("-p")) {
                    parsed.add(args[i]);
                    parsed.add(args[i+1]);
                    settingsInstance.setMaxPatient(Integer.parseInt(args[i+1]));
                } else if(arguments.equalsIgnoreCase("-h")) {
                    parsed.add(args[i]);
                    parsed.add(args[i+1]);
                    settingsInstance.setMaxHospital(Integer.parseInt(args[i+1]));
                } else if(arguments.equalsIgnoreCase("-bh")) {
                    parsed.add(args[i]);
                    parsed.add(args[i+1]);
                    settingsInstance.setMaxBridgehead(Integer.parseInt(args[i+1]));
                } else if (arguments.equalsIgnoreCase("-e")) {
                    parsed.add(args[i]);
                    String value = args[i+1];
                    parsed.add(value.toLowerCase());
                    switch (value.toLowerCase()) {
                        case "true":
                        case "t":
                            settingsInstance.setExpertMode(true);
                            break;
                        case "false":
                        case "f":
                            settingsInstance.setExpertMode(false);
                            break;
                    }
                } else if (arguments.equalsIgnoreCase("-fc")) {
                    parsed.add(args[i]);
                    parsed.add(args[i+1]);
                    int value = Integer.valueOf(args[i+1]);
                    settingsInstance.setMaxFrameCount(value);
                }
            }
        }
        if (parsed.size() != args.length) {
            System.out.println("SOME ARGS NOT RECOGNISED!");
        }
        //runtime init args parse code END

        //verification code START
        /*SPRT verifier;
        //ComfortZoneChecker comfortZoneChecker = new ComfortZoneChecker();
        
        // Existence
        MCIProperty property = new MCIProperty("RescuePatientProperty", "RescuedPatientRatioUpperThanValue", "MCIExistence", 0.02);
        // Absence
//        property.setThresholdValue(0); // RescueRate - TreatmentRate
        // Universality
//        property.setThresholdValue(1.0); // RescueRate
        // TransientStateProbability
//        property.setStateProbabilityValues(0.6, 60, 81);
        // SteadyStateProbability
//        property.setStateProbabilityValues(0.15, 0, 81);
        // MinimumDuration
//        property.setThresholdValue(10); // FF가 10명 이상 활동하고 있어야 한다.
//        property.setDuration(65); // 최소 65 Frame 이상
        // MaximumDuration
//        property.setThresholdValue(0); // Rescurate
//        property.setDuration(60); // 최대 60 Frame 이하
        // Bounded Existence
        property.setDuration(33); // Bounded Frame 40
        property.setState("Free"); // State가 Free인게 아님을 확인하기 위해
        // Precedence
        property.setPrevState("MoveToPatient");
        property.setState("FirstAid");
        
        MCIPropertyChecker existenceChecker = new MCIPropertyChecker();
        MCIAbsenceChecker absenceChecker = new MCIAbsenceChecker();
        MCIUniversalityChecker universalityChecker = new MCIUniversalityChecker();
        MCITransientSPChecker transientSPChecker = new MCITransientSPChecker();
        MCISteadySPChecker steadySPChecker = new MCISteadySPChecker();
        MCIMinimumDurationChecker minimumDurationChecker = new MCIMinimumDurationChecker();
        MCIMaximumDurationChecker maximumDurationChecker = new MCIMaximumDurationChecker();
        MCIBoundedExistenceChecker boundedExistenceChecker = new MCIBoundedExistenceChecker();
        MCIPrecedenceChecker precedenceChecker = new MCIPrecedenceChecker();
        
//        verifier = new SPRT(comfortZoneChecker);
        
//        verifier = new SPRT(existenceChecker);
//        verifier = new SPRT(absenceChecker);
//        verifier = new SPRT(universalityChecker);
//        verifier = new SPRT(transientSPChecker);
//        verifier = new SPRT(steadySPChecker);
//        verifier = new SPRT(minimumDurationChecker);
//        verifier = new SPRT(maximumDurationChecker);
//        verifier = new SPRT(boundedExistenceChecker);
        verifier = new SPRT(precedenceChecker);
        Pair<Pair<Integer, Boolean>, String> verificationResult;
        */
        //verification code END
        SoSSimulationProgram simulationEngine = new SoSSimulationProgram();
        simulationEngine.setRunning();
//        System.out.println("Get Running: "+ simulationEngine.getRunning());
        programStartTime = System.nanoTime();           // 첫번째 시뮬레이션까지 포함할려면 여기에 정의
        simulationEngine.run();
        simulationEngine.setSuper_counter();

        double satisfactionProb = 0;
        Boolean satisfaction = true;

//        for (int i = 1; i < 100; i++) {
////            programStartTime = System.nanoTime();           // 첫번째 시뮬레이션을 제외하려면 여기에 정의
//
////            System.out.println("inside for loop:" + simulationEngine.getRunning());
//            double theta = i * 0.01;
//
//            thetaStartTime = System.nanoTime();
//            verificationResult = verifier.verifyWithSimulationGUI(simulationEngine, property, 2000, theta);    //or T = 3
//            thetaEndTime = System.nanoTime();
//            System.out.println(i /(float)100 + " theta verification running time: " + (thetaEndTime - thetaStartTime) / (float)1000_000_000 + " sec");          // 한 theta 실행 시간
//
//            System.out.println(verificationResult.getValue());
//            if (satisfaction == true && !verificationResult.getKey().getValue()) {
//                satisfactionProb = theta;
//                satisfaction = false;
//            }
//
//        }
//        if (satisfaction == true) {
//            satisfactionProb = 1;
//        }
//        System.out.println("Verification property satisfaction probability: " + satisfactionProb);
        programEndTime = System.nanoTime();
        System.out.println("=== Total Program running time: " + (programEndTime - programStartTime) / (float)1000_000_000 + " sec");          // 전체 프로그램 실행 시간

//        new Thread(simulationEngine).start();
    }
}
