package main

import (
	"fmt"
	"sync"
	"time"
)

type Train struct {
	name          string
	targetTunnel  *Tunnel
	railwaySystem *RailwaySystem
}

func NewTrain(name string, targetTunnel *Tunnel, system *RailwaySystem) *Train {
	return &Train{name: name, targetTunnel: targetTunnel, railwaySystem: system}
}

func (t *Train) Run() {
	t.railwaySystem.RequestTunnelEntry(t, t.targetTunnel)
}

func (t *Train) GetName() string {
	return t.name
}

type Tunnel struct {
	mu    sync.Mutex
	name  string
	queue []*Train
}

func NewTunnel(name string) *Tunnel {
	return &Tunnel{name: name, queue: make([]*Train, 0)}
}

func (t *Tunnel) TryEnter(train *Train) bool {
	if t.mu.TryLock() {
		t.queue = append(t.queue, train)
		fmt.Println(train.GetName(), "has entered", t.name)
		return true
	}
	return false
}

func (t *Tunnel) Exit(train *Train) {
	fmt.Println(train.GetName(), "is exiting", t.name)
	t.mu.Unlock()
	t.queue = t.queue[1:]
}

func (t *Tunnel) GetName() string {
	return t.name
}

func (t *Tunnel) HasWaitingTrains() bool {
	return len(t.queue) > 0
}

type RailwaySystem struct {
	tunnel1 *Tunnel
	tunnel2 *Tunnel
}

const (
	WAITING_TIME   = 2 * time.Second
	IN_TUNNEL_TIME = 5 * time.Second
)

func NewRailwaySystem(t1, t2 *Tunnel) *RailwaySystem {
	return &RailwaySystem{tunnel1: t1, tunnel2: t2}
}

func (rs *RailwaySystem) RequestTunnelEntry(train *Train, preferredTunnel *Tunnel) {
	hasEntered := preferredTunnel.TryEnter(train)
	trainWaitingTime := 0 * time.Millisecond

	if !hasEntered {
		fmt.Println(train.GetName(), "is waiting...")

		for !hasEntered && trainWaitingTime < WAITING_TIME {
			hasEntered = preferredTunnel.TryEnter(train)
			trainWaitingTime += 1
			time.Sleep(1 * time.Millisecond)
		}
		if !hasEntered {
			fmt.Println(train.GetName(), "is changing tunnel...")
			alternateTunnel := rs.tunnel1
			if preferredTunnel == rs.tunnel1 {
				alternateTunnel = rs.tunnel2
			}
			rs.RequestTunnelEntry(train, alternateTunnel)
			return
		}
	}

	time.Sleep(IN_TUNNEL_TIME) // Simulate the time train spends in the tunnel
	preferredTunnel.Exit(train)
}

func main() {
	tunnel1 := NewTunnel("Tunnel 1")
	tunnel2 := NewTunnel("Tunnel 2")
	system := NewRailwaySystem(tunnel1, tunnel2)

	train1 := NewTrain("Train 1", tunnel1, system)
	train2 := NewTrain("Train 2", tunnel2, system)
	train3 := NewTrain("Train 3", tunnel1, system)

	go train1.Run()
	go train2.Run()
	go train3.Run()

	// Add a small delay so that the main thread does not terminate too quickly
	time.Sleep(15 * time.Second)
}
