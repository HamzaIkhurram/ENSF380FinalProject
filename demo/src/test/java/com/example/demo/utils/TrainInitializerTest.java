package com.example.demo.utils;

import com.example.demo.models.Train;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrainInitializerTest {

    @Test
    public void testInitializeTrains() {
        // Coordinates for red, green, and blue lines
        List<double[]> redCoordinates = List.of(
            new double[]{8.756969332695007, 14.790168762207031},
            new double[]{37.29621505737305, 40.158387184143066},
            new double[]{69.39208602905273, 56.50865650177002},
            new double[]{107.5646743774414, 57.451191902160645},
            new double[]{143.650785446167, 66.93851089477539},
            new double[]{179.11643600463867, 71.32914638519287},
            new double[]{215.9364471435547, 61.2137565612793},
            new double[]{251.06603240966797, 61.463523864746094},
            new double[]{282.7642593383789, 82.75336837768555},
            new double[]{305.29759979248047, 111.34457206726074},
            new double[]{320.2179298400879, 145.77654457092285},
            new double[]{355.52606201171875, 160.315185546875},
            new double[]{390.8341751098633, 174.85381698608398},
            new double[]{427.7744445800781, 180.87890243530273},
            new double[]{465.9560546875, 180.42970275878906},
            new double[]{501.0545883178711, 192.2490463256836},
            new double[]{532.0616683959961, 213.06708908081055},
            new double[]{550.3903350830078, 246.11367797851562},
            new double[]{587.2103271484375, 256.2290725708008},
            new double[]{624.0303955078125, 266.34448623657227},
            new double[]{627.5908050537109, 301.8144950866699},
            new double[]{627.5908050537109, 339.99871826171875},
            new double[]{627.9186019897461, 378.1806335449219},
            new double[]{629.1322479248047, 415.9777603149414},
            new double[]{661.8077239990234, 435.73499298095703},
            new double[]{699.1056213378906, 442.17488861083984},
            new double[]{736.9949493408203, 446.91102600097656},
            new double[]{770.4509887695312, 459.8792037963867},
            new double[]{795.2575073242188, 488.90802001953125},
            new double[]{827.7104339599609, 507.12171173095703},
            new double[]{863.6895141601562, 519.0896377563477},
            new double[]{901.8669281005859, 519.8099670410156},
            new double[]{940.0328216552734, 518.7252578735352},
            new double[]{978.1980285644531, 517.5200805664062},
            new double[]{1014.9868927001953, 526.3371047973633},
            new double[]{1048.9985809326172, 540.7568511962891},
            new double[]{1067.6383361816406, 574.082389831543},
            new double[]{1093.2731018066406, 601.4058456420898},
            new double[]{1123.6567077636719, 624.5114288330078},
            new double[]{1155.3055725097656, 645.8744201660156}
        );

        List<double[]> greenCoordinates = List.of(
            new double[]{15.65205430984497, 238.88040161132812},
            new double[]{55.05329704284668, 240.80241775512695},
            new double[]{94.32740783691406, 243.8137969970703},
            new double[]{133.11234092712402, 251.01670837402344},
            new double[]{171.12041091918945, 261.31572341918945},
            new double[]{208.69414901733398, 273.3301887512207},
            new double[]{246.15886306762695, 285.6811866760254},
            new double[]{283.64835357666016, 297.95032119750977},
            new double[]{321.67604064941406, 308.44070053100586},
            new double[]{359.7305450439453, 318.8260612487793},
            new double[]{398.13001251220703, 327.8612289428711},
            new double[]{436.67516326904297, 335.9985122680664},
            new double[]{475.96124267578125, 339.5699462890625},
            new double[]{515.2843246459961, 342.4329528808594},
            new double[]{554.7287521362305, 342.9732666015625},
            new double[]{594.1381225585938, 344.5406494140625},
            new double[]{633.5531005859375, 345.7541809082031},
            new double[]{673.0011901855469, 345.7541809082031},
            new double[]{712.4480590820312, 346.0019073486328},
            new double[]{751.8941650390625, 346.40035247802734},
            new double[]{791.3053283691406, 345.47742462158203},
            new double[]{830.6752166748047, 342.99462890625},
            new double[]{870.0445709228516, 340.5045471191406},
            new double[]{909.3975219726562, 337.76692962646484},
            new double[]{948.7505493164062, 335.02931213378906},
            new double[]{988.0705413818359, 331.8790588378906},
            new double[]{1027.3587951660156, 328.33219146728516},
            new double[]{1066.647201538086, 324.7853240966797},
            new double[]{1105.736099243164, 319.8360595703125},
            new double[]{1144.4563446044922, 312.2931442260742}
        );

        List<double[]> blueCoordinates = List.of(
            new double[]{7.895084381103516, 669.8231658935547},
            new double[]{38.60073184967041, 646.0697326660156},
            new double[]{61.51604747772217, 616.2509307861328},
            new double[]{77.27171993255615, 581.0547409057617},
            new double[]{116.08906364440918, 581.5792999267578},
            new double[]{154.90771865844727, 581.9108734130859},
            new double[]{193.7286148071289, 581.9108734130859},
            new double[]{222.53331756591797, 567.6107559204102},
            new double[]{243.7357292175293, 538.6774978637695},
            new double[]{279.514461517334, 523.6128005981445},
            new double[]{318.27272033691406, 523.3027038574219},
            new double[]{355.64237213134766, 518.4756240844727},
            new double[]{388.0275115966797, 497.0684585571289},
            new double[]{424.62781524658203, 489.363037109375},
            new double[]{463.4455261230469, 488.86537170410156},
            new double[]{497.10103607177734, 470.2897033691406},
            new double[]{532.5816497802734, 454.95606994628906},
            new double[]{569.1306838989258, 441.8706283569336},
            new double[]{604.2765197753906, 425.50035095214844},
            new double[]{621.9381484985352, 397.8986511230469},
            new double[]{620.9301528930664, 359.09085845947266},
            new double[]{620.6956634521484, 320.2730522155762},
            new double[]{620.6956634521484, 281.45217514038086},
            new double[]{639.3739852905273, 250.3680763244629},
            new double[]{669.1370544433594, 228.0419692993164},
            new double[]{707.88232421875, 225.62039947509766},
            new double[]{740.2182159423828, 211.14644622802734},
            new double[]{763.8876495361328, 180.3760871887207},
            new double[]{794.6264038085938, 158.52565383911133},
            new double[]{831.0381469726562, 146.61880111694336},
            new double[]{869.7518310546875, 143.7358627319336},
            new double[]{908.4877166748047, 141.63983917236328},
            new double[]{947.3024139404297, 142.33295059204102},
            new double[]{986.1171875, 143.0260715484619},
            new double[]{1016.2791595458984, 122.77095413208008},
            new double[]{1021.4313659667969, 86.80495834350586},
            new double[]{1041.0194549560547, 58.29576110839844},
            new double[]{1074.136215209961, 39.62751007080078},
            new double[]{1112.3410186767578, 32.73813581466675},
            new double[]{1149.6964416503906, 22.2473726272583}
        );

        // Initialize trains
        List<Train> trains = TrainInitializer.initializeTrains(redCoordinates, greenCoordinates, blueCoordinates);

        // Check the size of the train list
        assertEquals("The number of trains should be 12", 12, trains.size());

        // Verify the first train's details
        Train firstTrain = trains.get(0);
        assertEquals("R1", firstTrain.getId());
        assertEquals("R", firstTrain.getLine());
        assertEquals(0, firstTrain.getCurrentStationIndex());
        assertEquals("forward", firstTrain.getDirection());
        assertEquals(8.756969332695007, firstTrain.getX(), 0.001);
        assertEquals(14.790168762207031, firstTrain.getY(), 0.001);

        // Verify the second train's details
        Train secondTrain = trains.get(1);
        assertEquals("G1", secondTrain.getId());
        assertEquals("G", secondTrain.getLine());
        assertEquals(0, secondTrain.getCurrentStationIndex());
        assertEquals("forward", secondTrain.getDirection());
        assertEquals(15.65205430984497, secondTrain.getX(), 0.001);
        assertEquals(238.88040161132812, secondTrain.getY(), 0.001);

        // Verify the third train's details
        Train thirdTrain = trains.get(2);
        assertEquals("B1", thirdTrain.getId());
        assertEquals("B", thirdTrain.getLine());
        assertEquals(0, thirdTrain.getCurrentStationIndex());
        assertEquals("forward", thirdTrain.getDirection());
        assertEquals(7.895084381103516, thirdTrain.getX(), 0.001);
        assertEquals(669.8231658935547, thirdTrain.getY(), 0.001);
    }

    @Test
    public void testInitializeTrainsWithEmptyList() {
        List<Train> trains = TrainInitializer.initializeTrains(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertTrue("The train list should be empty", trains.isEmpty());
    }

    @Test
    public void testInitializeTrainsWithNullList() {

        List<Train> trains =TrainInitializer.initializeTrains(null, null, null);
        assertNull(trains);
    }
}
