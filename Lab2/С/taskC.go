package main

import (
	"container/list"
	"fmt"
	"math/rand"
)

func fight(a int, b int, o chan int) {
	if rand.Intn(2) == 1 {
		o <- b
	} else {
		o <- a
	}
}

func main() {
	listOfRivals := list.New()
	listOfRivals.Init()
	for i := 0; i < 32; i++ {
		listOfRivals.PushBack(0)
		listOfRivals.PushBack(1)
	}
	for listOfRivals.Len() > 1 {
		o := make(chan int)
		val := listOfRivals.Front()
		for val != nil {
			go fight(val.Value.(int), val.Next().Value.(int), o)
			val = val.Next().Next()
		}
		iterations := listOfRivals.Len() / 2
		listOfRivals = list.New()
		listOfRivals.Init()
		for i := 0; i < iterations; i++ {
			listOfRivals.PushBack(<-o)
		}
	}
	switch listOfRivals.Front().Value.(int) {
	case 0:
		fmt.Println("Winner Guan-Yin!")
		break
	case 1:
		fmt.Println("Winner Guan-Yan!")
		break
	default:
		break
	}
}
