package core;

import misc.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Project: NewSimulator
 * Created by IntelliJ IDEA
 * Author: Sumin Park <smpark@se.kaist.ac.kr>
 * Github: https://github.com/sumin0407/NewSimulator.git
 */

public abstract class SoSObject {

    public SoSObject() {

    }
    public SoSObject(String name) {
        this.name = name;
    }

    SoSObject parent;
    public void setParent(SoSObject parent) {
        this.parent = parent;
    }

    Queue<SoSObject> children = new LinkedList<>();
    public void addChild(SoSObject child) {
        child.setParent(this);
        children.remove(child);
        children.add(child);
    }
    public void removeChild(SoSObject child) {
        child.parent = null;
        children.remove(child);
    }

    // << Field: position >>
    public final Position position = new Position();
    public void setPosition(Position position) {
        //this.position = position;
        this.position.set(position);
    }
    public void setPosition(int x, int y) {
        this.position.set(x, y);
    }
    public Position getPosition() {
        return position;
    }
    // << Field: position >>


    // << Field: currentImage >>
    SoSImage currentImage;
    public void setCurrentImage(SoSImage currentImage) {
        this.currentImage = currentImage;
    }

    public SoSImage getCurrentImage() {
        return currentImage;
    }
    // << Field: currentImage >>


    // << Field: canUpdate >>
    // 이 필드가 true일 때만 SoSObject Update
    boolean _canUpdate = true;
    public void canUpdate(boolean _canUpdate) {
        this._canUpdate = _canUpdate;
    }
    // << Field: canUpdate >>


    // << Field: canRender >>
    // 이 필드가 true일 때만 SoSObject render
    boolean _visible = true;
    public void visible(boolean _visible) {
        this._visible = _visible;
    }
    // << Field: canRender >>


    // << Method: start >>
    // 초기화 이후(init) update 이전 동작 코드 작성
    public void start() { }
    // << Method: start >>


    // << Method: update >>
    // 매 프레임마다 호출
    // 상속 불가능. 외부용
    public final void update() {
        if(_canUpdate) {
            onUpdate();

            children.add(null);
            while(true) {
                SoSObject child = children.poll();
                if(child == null) break;
                children.add(child);
                child.update();
            }
        }
    }

    // 상속 가능, 내부용
    protected void onUpdate() {

    }
    // << Method: update >>


    // << Method: render >>
    // 매 프레임마다 호출
    // 상속 불가능. 외부용
    public final void render(Graphics2D g) {
        if(_visible) {

            Graphics2D localGraphic = (Graphics2D)g.create();
            localGraphic.translate(position.x * Map.tileSize.width, position.y * Map.tileSize.height);
            onRender(localGraphic);

            children.add(null);
            while(true) {
                SoSObject child = children.poll();
                if(child == null) break;
                children.add(child);
                child.render(localGraphic);
            }
        }
    }

    // 상속 가능, 내부용
    protected void onRender(Graphics2D g) {
    }
    // << Method: render >>


    // << Method: clear >>
    // 정리 코드 작성
    public void clear() {
        if(parent != null) {
            parent.removeChild(this);
            parent = null;
        }
        children.forEach(child -> {
            if(child != null) {
                child.clear();
            }
        });
    }
    // << Method: clear >>


    // 자기 자신 삭제
    public void remove() {
        clear();
    }


    public String name = "";

    public SoSObject findObject(String targetName) {
        if(name.equalsIgnoreCase(targetName)) {
//        if(name == targetName) {
            return this;
        } else {
            for (SoSObject child : children) {
                if(child != null) {
                    SoSObject object = child.findObject(targetName);
                    if (object != null) {
                        return object;
                    }
                }
            }
        }
        return null;
    }

    public void recvMsg(Msg msg) {
        
    }

    public int distantTo(SoSObject target) {
        return Math.abs(position.x - target.position.x) + Math.abs(position.y - target.position.y);
    }

    public static int distanceBetween(SoSObject left, SoSObject right) {
        return Math.abs(left.position.x - right.position.x) + Math.abs(left.position.y - right.position.y);
    }

    public SoSObject nearestObject(ArrayList<SoSObject> targets) {
        SoSObject minObject = null;
        for(SoSObject object: targets) {
            if(minObject == null) {
                minObject = object;
            } else {
                if(distanceBetween(this, minObject) >= distanceBetween(this, object)) {
                    minObject = object;
                }
            }
        }
        return minObject;
    }

//    public static SoSObject nearestObject(SoSObject from, ArrayList<SoSObject> targets) {
//        SoSObject minObject = null;
//        for(SoSObject object: targets) {
//            if(minObject == null) {
//                minObject = object;
//            } else {
//                if(distanceBetween(from, minObject) >= distanceBetween(from, object)) {
//                    minObject = object;
//                }
//            }
//        }
//        return minObject;
//    }
}
