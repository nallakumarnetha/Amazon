package com.example.demo;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.BiPredicate;

public class Demo {
	public static void main(String [] args) {
		

		List<String> list = List.of("aaa", "bbb", "ccc");
		list.stream()
		.filter(s-> {
			System.out.println("hi");
		return s.length()==3;
		})
		.filter(s->s.length()>=3)
		.forEach(System.out::println);
		
		double d = Double.NaN;
		System.out.println(Double.isNaN(d));

		
	}
}




