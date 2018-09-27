package core;

import agents.FireFighter;
import agents.Patient;
import misc.Position;
import misc.Size;

import java.awt.*;
import java.util.ArrayList;

public class World extends SoSObject{

    public static final int maxPatient = 100;
    public static final int maxFireFighter = 5;

    Map map;
    public MsgRouter router;

    public World() {
        map = new Map();
        addChild(map);

        // 맵 생성 후에 라우터 생성해야함
        // 안그러면 널 에러
        router = new MsgRouter(this);

        createPatients();
        createFireFighters();
    }

    private void createPatients() {
        for (int i = 0; i < maxPatient; i++) {
            Patient patient = new Patient(this, "Patient");
            patient.setStatus(Patient.Status.random());
            Position randomPosition = GlobalRandom.nextPosition(Map.mapSize.width, Map.mapSize.height);
            patient.position.set(randomPosition.x, randomPosition.y);
            addChild(patient);
            map.addObject(randomPosition.x, randomPosition.y, patient);
        }
    }

    private void createFireFighters() {
        for (int i = 0; i < maxFireFighter; i++) {
            FireFighter ff = new FireFighter(this, "FireFighter" + (i + 1));
            addChild(ff);
            ff.setPosition(0, 0);
        }
    }

    @Override
    public void onRender(Graphics2D g) {
        Rectangle rect = g.getDeviceConfiguration().getBounds();
        g.setColor(new Color(100, 100, 100));
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    public Map getMap() {
        return map;
    }
}
