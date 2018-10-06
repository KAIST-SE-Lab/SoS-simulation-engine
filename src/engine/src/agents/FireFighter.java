package agents;

import action.Action;
import action.firefighteraction.FireFighterCollaborativeAction;
import action.firefighteraction.FireFighterAction;
import action.firefighteraction.FireFighterSearch;
import core.*;
import core.Map;
import misc.Position;
import misc.Time;

import java.awt.*;
import java.util.*;

public class FireFighter extends CS {

    public World world;
    public Map individualMap;

    public Queue<Tile> unvisitedTiles;
    public final ArrayList<Patient> patientsMemory = new ArrayList<>();

    public ImageObject transferImage;
    public ImageObject defaultImage;
    public ImageObject moveToPatient;
    public int totalDistance = 0;

    public int sightRange = 11;
    public int communicationRange = 2;
    public FireFighter(World world, String name) {
        super(world, name);
        this.world = world;

        transferImage = new ImageObject("src/engine/resources/transfer.png");
        defaultImage = new ImageObject("src/engine/resources/ff30x30.png");
        moveToPatient = new ImageObject("src/engine/resources/moveToPatient.png");

        transferImage.visible(false);
        defaultImage.visible(true);
        moveToPatient.visible(false);

        addChild(transferImage);
        addChild(defaultImage);
        addChild(moveToPatient);

        individualMap = new Map();
        LinkedList<Tile> temp = new LinkedList<>(individualMap.getTiles());
        //LinkedList<Tile> temp= new LinkedList<>(world.getMap().getTiles());
        Collections.shuffle(temp);

        unvisitedTiles = temp;
        //currentAction = new FireFighterCollaborativeAction(this);
        currentAction = new FireFighterSearch(this);
    }



    @Override
    public void setPosition(int x, int y) {
        worldMap.remove(this);
        super.setPosition(x, y);
        worldMap.add(this);
    }
    @Override
    public void setPosition(Position position) {
        worldMap.remove(this);
        super.setPosition(position);
        worldMap.add(this);
    }

    @Override
    public void clear() {
        world = null;
    }
    public int getSightRange() {
        return sightRange;
    }
    public World getWorld() {
        return world;
    }


    @Override
    public void onRender(Graphics2D graphics2D) {
        graphics2D.setColor(Color.red);
        graphics2D.setFont(new Font("default", Font.BOLD, 16));
        graphics2D.drawChars(name.toCharArray(), 0, name.length(), 0, 0);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        router.broadcast(this,
                new Msg()
                        .setFrom(name)
                        .setTitle("individual map")
                        .setTo("broadcast")
                        .setData(individualMap),
                position, communicationRange);
        router.broadcast(this,
                new Msg()
                        .setFrom(name)
                        .setTitle("patientsMemory")
                        .setTo("broadcast")
                        .setData(patientsMemory),
                position, communicationRange);
    }

    public ArrayList<Patient> observe() {
        ArrayList<Patient> foundPatient = new ArrayList<>();

        for(int y = position.y - (sightRange / 2); y <= position.y + (sightRange / 2); ++y) {
            for(int x = position.x - (sightRange / 2); x <= position.x + (sightRange / 2); ++x) {
                Tile worldTile = worldMap.getTile(x, y);
                if(worldTile != null) {
                    foundPatient.addAll(worldTile.patients);
                }
            }
        }

        foundPatient.removeAll(patientsMemory);
        patientsMemory.addAll(foundPatient);

        return foundPatient;
    }

    public void markVisitedTiles() {
        for(int y = position.y - (sightRange / 2); y <= position.y + (sightRange / 2); ++y) {
            for(int x = position.x - (sightRange / 2); x <= position.x + (sightRange / 2); ++x) {
                worldMap.visited(x, y, true);
                individualMap.visited(x, y, true);
            }
        }
    }

    public Patient selectTargetPatient(ArrayList<Patient> patients) {

        ArrayList<SoSObject> seriousPatients = new ArrayList<>();
        patients.forEach(patient -> {
            if(patient.isSerious()) {
                seriousPatients.add(patient);
            }
        });
        if(!seriousPatients.isEmpty()) {
            return (Patient)nearestObject(seriousPatients);

        } else {
            return (Patient)nearestObject(new ArrayList<>(patients));
        }
    }

    int moveDelay = 3;
    int frameCounter = moveDelay;
    @Override
    public void moveTo(Position destination) {
        if(frameCounter <= 0) {
            frameCounter = moveDelay;
            Position nextPosition = nextPosition(destination);
            if(nextPosition != null) {
                setPosition(nextPosition);
            }
        }
        frameCounter--;
    }

    @Override
    public void recvMsg(Msg msg) {
        if(msg.from.startsWith(World.fireFighterPrefix)) {
            if(msg.title == "individual map") {
                Map othersMap = (Map)msg.data;

                // 방문 정보 업데이트
                othersMap.getTiles().forEach(tile -> {
                    if(tile.isVisited()) {
                        individualMap.getTile(tile.position.x, tile.position.y).visited(true);
                    }
                });
            }
            else if(msg.title == "patientsMemory") {
                ArrayList<Patient> othersMemory = (ArrayList<Patient>)msg.data;
                if(Time.getFrameCount() > 400) {
                    int a = 10;
                }
                patientsMemory.removeAll(othersMemory);
                othersMemory.forEach(patient -> {

//                    patientsMemory.add(patient);
                    if(patient.isSaved == false) {
                        patientsMemory.add(patient);
                    }
                });
            }
        }
        super.recvMsg(msg);
    }
}
