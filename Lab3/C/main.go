package main

import (
	list "container/list"
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func smoker(c0 int, list *list.List, wg *sync.WaitGroup, l *sync.Mutex) {
	for {
		if list.Len() != 0 {
			l.Lock()
			if list.Len() == 2 {
				c1 := list.Front().Value
				list.Remove(list.Front())

				c2 := list.Front().Value
				list.Remove(list.Front())

				if c1 != c0 && c2 != c0 {
					fmt.Printf("%d is smoking \n", c0)
					time.Sleep(1000 * time.Millisecond)
					wg.Done()
				} else {
					//fmt.Printf("%d failed \n", c0)
					list.PushBack(c1)
					list.PushBack(c2)
				}
			}
			l.Unlock()
		}
	}
}

func producer(ls *list.List, wg *sync.WaitGroup) {
	for {
		v0 := rand.Intn(3)
		v1 := rand.Intn(3)

		if v0 == v1 {
			v1 = (v1 + 1) % 3
		}

		ls.PushBack(v0)
		ls.PushBack(v1)

		fmt.Printf("produced %d %d \n", v0, v1)

		wg.Add(1)
		wg.Wait()
	}
}

func main() {
	var list list.List

	var wg sync.WaitGroup

	var l sync.Mutex

	go smoker(0, &list, &wg, &l)
	go smoker(1, &list, &wg, &l)
	go smoker(2, &list, &wg, &l)

	go producer(&list, &wg)

	for {

	}
}
