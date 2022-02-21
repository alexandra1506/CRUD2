package com.javarush.task.task17.task1711;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* 
CRUD 2
*/

public class Solution {
    public static volatile List<Person> allPeople = new ArrayList<>();

    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    public static void main(String[] args) {

        //args = new String[]{"-c", "Васильев Василий", "м", "04/01/1990", "Александрова Александра", "ж", "18/05/1999"};
        //args = new String[]{"-u", "0", "Бла бла", "ж", "04/01/1977", "1", "Александров Александр", "м", "18/05/1998"};
        //args = new String[]{"-d", "0", "1"};
       // args = new String[]{"-i", "0", "1"};

        // Create -c name1 sex1 bd1 name2 sex2 bd2 ...
        switch (args[0]) {

            case "-c":
                synchronized (allPeople) {
                    for (int i = 1; i < args.length; i = i + 3) {
                        if (args[i + 1].equals("м")) {
                            try {
                                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(args[i + 2]);
                                allPeople.add(Person.createMale(args[i], date1));
                                System.out.println(allPeople.size()-1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else if (args[i + 1].equals("ж")) {
                            try {
                                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(args[i + 2]);
                                allPeople.add(Person.createFemale(args[i], date1));
                                System.out.println(allPeople.size()-1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                }

                // update -u id1 name1 sex1 bd1 id2 name2 sex2 bd2 ...
            case "-u":
                synchronized (allPeople) {
                    for (int i = 1; i < args.length; i = i + 4) {
                        allPeople.get(Integer.parseInt(args[i])).setName(args[i + 1]);

                        if (args[i + 2].equals("м")) {
                            allPeople.get(Integer.parseInt(args[i])).setSex(Sex.MALE);
                        } else if (args[i + 2].equals("ж")) {
                            allPeople.get(Integer.parseInt(args[i])).setSex(Sex.FEMALE);
                        }

                        try {
                            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(args[i + 3]);
                            allPeople.get(Integer.parseInt(args[i])).setBirthDate(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;

            // Delete -d id1 id2 id3 id4 ...
            case "-d":
                synchronized (allPeople) {
                    for (int i = 1; i < args.length; i++) {
                        allPeople.get(Integer.parseInt(args[i])).setName(null);
                        allPeople.get(Integer.parseInt(args[i])).setSex(null);
                        allPeople.get(Integer.parseInt(args[i])).setBirthDate(null);
                    }
                }
                break;

                // info -i id1 id2 id3 id4 ...
            case "-i":
                synchronized (allPeople) {
                    for (int i = 1; i < args.length; i++) {
                        Person person = allPeople.get(Integer.parseInt(args[i]));

                        System.out.println(person.getName());
                        if(person.getSex() == Sex.MALE) {
                            System.out.println("м");
                        } else if (person.getSex() == Sex.FEMALE)
                            System.out.println("ж");
                        System.out.println(new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).
                                format(person.getBirthDate()));                    }
                }
                break;
        }

    }
}
