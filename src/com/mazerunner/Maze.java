package com.mazerunner;

import java.util.*;

public class Maze {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private List<String> mapLines;
    private List<String> mapLinesConverted = new ArrayList<>();

    private int currentPositionX;
    private int currentPositionY;

    private List<Integer> positionXHistory = new ArrayList<>();
    private List<Integer> positionYHistory = new ArrayList<>();

    public Maze(List<String> lines) {
        startX = Integer.parseInt(lines.get(1).split(" ")[0]);
        startY = Integer.parseInt(lines.get(1).split(" ")[1]);
        endX = Integer.parseInt(lines.get(2).split(" ")[0]);
        endY = Integer.parseInt(lines.get(2).split(" ")[1]);

        currentPositionX = startX;
        currentPositionY = startY;
        positionXHistory.add(currentPositionX);
        positionYHistory.add(currentPositionY);

        this.mapLines = lines;
        mapLines.remove(mapLines.get(0));
        mapLines.remove(mapLines.get(0));
        mapLines.remove(mapLines.get(0));

        convertMapLines();
    }

    public void convertMapLines() {
        for(String line : mapLines){
            mapLinesConverted.add(line.replace("1", "#").replace(" ", "").replace("0", " "));
        }
    }

    public Map<String, Character> getMoves(){
        Map<String, Character> moves = new HashMap<>();
        moves.put("N", mapLinesConverted.get(currentPositionY-1).charAt(currentPositionX));
        moves.put("E", mapLinesConverted.get(currentPositionY).charAt(currentPositionX+1));
        moves.put("S", mapLinesConverted.get(currentPositionY+1).charAt(currentPositionX));
        moves.put("W", mapLinesConverted.get(currentPositionY).charAt(currentPositionX-1));
        return moves;
    }

    private boolean isRestep(int positionX, int positionY){
        for(int i = 0; i < positionXHistory.size(); i++) {
            if (positionX == positionXHistory.get(i) && positionY == positionYHistory.get(i)) {
                return true;
            }
        }
        return false;
    }

    public void navigate(){
        List<String> routeTaken = new ArrayList<>();
        List<String> badRoutes = new ArrayList<>();

        while (currentPositionX != endX || currentPositionY != endY){
            boolean routeFound = false;

            int x = 0;
            int y = 0;

            try{
                getMoves();
            }catch (Exception e){

                String lastBadRoute = routeTaken.get(routeTaken.size()-1);

                badRoutes.add(Integer.parseInt(lastBadRoute.split(" ")[0]) + " " + Integer.parseInt(lastBadRoute.split(" ")[1]));

                currentPositionX = startX;
                currentPositionY = startY;

                positionXHistory.clear();
                positionYHistory.clear();

            }


            for(Map.Entry entry : getMoves().entrySet()){
                Character directionCharacter = (Character) entry.getValue();

                if(directionCharacter.equals(' ')){
                    String key = (String) entry.getKey();

                    int newPositionX = 0;
                    int newPositionY = 0;

                    if (key.equalsIgnoreCase("N")) {
                        newPositionY = currentPositionY - 1;
                        newPositionX = currentPositionX;
                    } else if (key.equalsIgnoreCase("E")) {
                        newPositionX = currentPositionX + 1;
                        newPositionY = currentPositionY;
                    } else if (key.equalsIgnoreCase("S")) {
                        newPositionY = currentPositionY + 1;
                        newPositionX = currentPositionX;
                    } else if (key.equalsIgnoreCase("W")) {
                        newPositionX = currentPositionX - 1;
                        newPositionY = currentPositionY;
                    }


                    if(isRestep(newPositionX, newPositionY) || mapLinesConverted.get(newPositionY).charAt(newPositionX) == '#'){
                        continue;
                    }

                    if(badRoutes.contains(newPositionX + " " + newPositionY)){
                        continue;
                    }
                    // save bad routes along with current route
                    if(routeFound){
                        routeTaken.add(x + " " + y);
                    } else {
                        x = newPositionX;
                        y = newPositionY;
                        routeFound = true;
                    }
                }
            }

            currentPositionX = x;
            currentPositionY = y;
            positionXHistory.add(currentPositionX);
            positionYHistory.add(currentPositionY);
        }

    }

    public String getMapSolved() {
        String map = "";
        for(int y = 0; y < mapLines.size(); y++){
            String line = mapLines.get(y).replace(" ", "");

            for(int i = 0; i < positionXHistory.size(); i++){
                if(positionYHistory.get(i) == y){
                    String startString = line.substring(0, positionXHistory.get(i));
                    String endString = line.substring(positionXHistory.get(i) + 1);
                    line = startString + "X" + endString;
                }
            }

            //split the line in two
            if(y == startY){
                String startString = line.substring(0, startX);
                String endString = line.substring(startX + 1);
                line = startString + "S" + endString;
            }

            if(y == endY){
                String startString = line.substring(0, endX);
                String endString = line.substring(endX + 1);
                line = startString + "E" + endString;
            }
            map = map.concat("\n" + line.replace("1", "#").replace("0", " "));
        }
        return map.substring(1);
    }


}
