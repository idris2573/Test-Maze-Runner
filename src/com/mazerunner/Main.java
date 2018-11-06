package com.mazerunner;

public class Main {

    public static void main(String[] args) throws Exception{
        if(args.length == 0){
            System.out.println("Please enter the full path to the maze file");
            return;
        }
        ReadFile readFile = new ReadFile(args[0]);
        if(readFile.getLines().isEmpty()){
            return;
        }
        Maze maze = new Maze(readFile.getLines());
        maze.navigate();
        System.out.println(maze.getMapSolved());
    }
}
