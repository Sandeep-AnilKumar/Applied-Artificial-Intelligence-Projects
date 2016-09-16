;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;GLOBAL DECLARATION;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defglobal ?*b* = nil)
(defglobal ?*f* = nil)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;TEMPLATE DECLARATION;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deftemplate Movement (slot front) (slot left) (slot right) 
    (slot reverse) (slot fly) (slot battery (default 100)) (slot fuel (default 100)))
(deftemplate Stop (slot front) (slot left) (slot right) 
    (slot reverse) (slot fly) (slot battery (default 100)) (slot fuel (default 100)))
(deftemplate Fly (slot front) (slot left) (slot right) 
    (slot reverse) (slot fly) (slot battery (default 100)) (slot fuel (default 100)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;FACTS DECLARATION;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deffacts initial-facts (Movement (front TRUE) (left TRUE) (right TRUE) 
        (reverse TRUE) (fly TRUE))
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;KNOWLEDGE BASE;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defmodule startDrone)

(defrule moveForward1
    ?p <- (Movement {battery > 10 && fuel > 10} (front TRUE))
    =>
    (if (eq (enterBatteryAndFuel "move forward") TRUE) then
    (printout t crlf "Drone is moving forward" crlf)
    (assert (Movement (front FALSE) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defmodule afterStart)

(defrule moveLeft1
    ?p <- (Movement {battery > 10 && fuel > 10} (front FALSE) (left TRUE))
    =>
    (if (eq (enterBatteryAndFuel "take left") TRUE) then
    (printout t crlf "Drone is taking a left turn" crlf)
    (assert (Movement (front TRUE) (left FALSE) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule moveLeft2
    ?p <- (Movement {battery > 10 && fuel > 10} (front FALSE) (left TRUE) (right TRUE) (reverse TRUE))
    =>
    (if (eq (enterBatteryAndFuel "take left") TRUE) then
    (printout t crlf "Drone is taking a left turn" crlf)
    (assert (Movement (front ?p.front) (left FALSE) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule moveLeft3
    ?p <- (Movement {battery > 10 && fuel > 10} (front FALSE) (left TRUE) (right FALSE) (reverse TRUE))
    =>
    (if (eq (enterBatteryAndFuel "take left") TRUE) then
    (printout t crlf "Drone is taking a left turn" crlf)
    (assert (Movement (front ?p.front) (left FALSE) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule moveLeft4
    ?p <- (Movement {battery > 10 && fuel > 10} (front TRUE) (left TRUE) (right TRUE) (reverse TRUE))
    =>
    (if (eq (enterBatteryAndFuel "take left") TRUE) then
    (printout t crlf "Drone is taking a left turn" crlf)
    (assert (Movement (front ?p.front) (left FALSE) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule moveRight1
    ?p <- (Movement {battery > 10 && fuel > 10} (front FALSE) (left FALSE) (right TRUE))
    =>
    (if (eq (enterBatteryAndFuel "take right") TRUE) then
    (printout t crlf "Drone is taking a right turn" crlf)
    (assert (Movement (front ?p.front) (left ?p.left) (right FALSE) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule moveRight2
    ?p <- (Movement {battery > 10 && fuel > 10} (front FALSE) (left FALSE) (right TRUE) (reverse TRUE))
    =>
    (if (eq (enterBatteryAndFuel "take right") TRUE) then
    (printout t crlf "Drone is taking a right turn" crlf)
    (assert (Movement (front ?p.front) (left ?p.left) (right FALSE) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule moveRight3
    ?p <- (Movement {battery > 10 && fuel > 10} (front FALSE) (left FALSE) (right TRUE) (reverse FALSE))
    =>
    (if (eq (enterBatteryAndFuel "take right") TRUE) then
    (printout t crlf "Drone is taking a right turn" crlf)
    (assert (Movement (front ?p.front) (left ?p.left) (right FALSE) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule moveRight4
    ?p <- (Movement {battery > 10 && fuel > 10} (front TRUE) (left TRUE) (right TRUE) (reverse FALSE))
    =>
    (if (eq (enterBatteryAndFuel "take right") TRUE) then
    (printout t crlf "Drone is taking a right turn" crlf)
    (assert (Movement (front ?p.front) (left ?p.left) (right FALSE) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule takeReverse1
    ?p <- (Movement {battery > 10 && fuel > 10} (front FALSE) (left FALSE) (right FALSE) (reverse TRUE))
    =>
    (if (eq (enterBatteryAndFuel "take reverse") TRUE) then
    (printout t crlf "Drone is taking reverse" crlf)
    (assert (Movement (front ?p.front) (left ?p.left) (right ?p.right) (reverse FALSE) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule takeReverse2
    ?p <- (Movement {battery > 10 && fuel > 10} (front TRUE) (left FALSE) (right FALSE) (reverse TRUE))
    =>
    (if (eq (enterBatteryAndFuel "take reverse") TRUE) then
    (printout t crlf "Drone is taking reverse" crlf)
    (assert (Movement (front ?p.front) (left ?p.left) (right ?p.right) (reverse FALSE) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule takeReverse3
    ?p <- (Movement {battery > 10 && fuel > 10} (front TRUE) (left FALSE) (right TRUE) (reverse TRUE))
    =>
    (if (eq (enterBatteryAndFuel "take reverse") TRUE) then
    (printout t crlf "Drone is taking reverse" crlf)
    (assert (Movement (front ?p.front) (left ?p.left) (right ?p.right) (reverse FALSE) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule flyUp1
    ?p <- (Movement {battery > 10 && fuel > 10} (front FALSE) (left FALSE) (right FALSE) (reverse FALSE) (fly TRUE))
    =>
    (if (eq (enterBatteryAndFuel "fly up in the air!") TRUE) then
    (printout t crlf "Drone is flying up!" crlf)
    (assert (Fly (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly FALSE) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule flyUp2
    ?p <- (Movement {battery > 10 && fuel > 10} (front FALSE) (left TRUE) (right FALSE) (reverse TRUE) (fly TRUE))
    =>
    (if (eq (enterBatteryAndFuel "fly up in the air!") TRUE) then
    (printout t crlf "Drone is flying up!" crlf)
    (assert (Fly (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly FALSE) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule flyUp3
    ?p <- (Movement {battery > 10 && fuel > 10} (front TRUE) (left FALSE) (right FALSE) (reverse TRUE) (fly TRUE))
    =>
    (if (eq (enterBatteryAndFuel "fly up in the air!") TRUE) then
    (printout t crlf "Drone is flying up!" crlf)
    (assert (Fly (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly FALSE) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule flyUp4
    ?p <- (Movement {battery > 10 && fuel > 10} (front FALSE) (left TRUE) (right TRUE) (reverse FALSE) (fly TRUE))
    =>
    (if (eq (enterBatteryAndFuel "fly up in the air!") TRUE) then
    (printout t crlf "Drone is flying up!" crlf)
    (assert (Fly (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly FALSE) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule changeToLeftLane
    ?p <- (Movement {battery > 10 && fuel > 10} (front TRUE) (left TRUE))
    =>
    (if (eq (enterBatteryAndFuel "change to left lane") TRUE) then
    (printout t crlf "Drone has changed lanes to left" crlf)
    (assert (Movement (front FALSE) (left FALSE) (right ?p.left) (reverse ?p.left) 
                (fly ?p.left) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule changeToRightLane
    ?p <- (Movement {battery > 10 && fuel > 10} (front TRUE) (right TRUE))
    =>
    (if (eq (enterBatteryAndFuel "change to right lane") TRUE) then
    (printout t crlf "Drone has changed lanes to right" crlf)
    (assert (Movement (front FALSE) (left ?p.left) (right FALSE) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule flyToLeft
    ?p <- (Movement {battery > 10 && fuel > 10} (left TRUE) (fly TRUE))
    =>
    (if (eq (enterBatteryAndFuel "fly to left") TRUE) then
    (printout t crlf "Drone is flying to left" crlf)
    (assert (Fly (front ?p.front) (left FALSE) (right ?p.right) (reverse ?p.reverse) 
                (fly FALSE) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule flyToRight
    ?p <- (Movement {battery > 10 && fuel > 10} (right TRUE) (fly TRUE))
    =>
    (if (eq (enterBatteryAndFuel "fly to right") TRUE) then
    (printout t crlf "Drone is flying to right" crlf)
    (assert (Fly (front ?p.front) (left ?p.left) (right FALSE) (reverse ?p.reverse) 
                (fly FALSE) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule flyForward
    ?p <- (Movement {battery > 10 && fuel > 10} (front TRUE) (fly TRUE))
    =>
    (if (eq (enterBatteryAndFuel "fly forward") TRUE) then
    (printout t crlf "Drone is flying to right" crlf)
    (assert (Fly (front FALSE) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly FALSE) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))

(defrule flyBackwards
    ?p <- (Movement {battery > 10 && fuel > 10} (reverse TRUE) (fly TRUE))
    =>
    (if (eq (enterBatteryAndFuel "fly backwards") TRUE) then
    (printout t crlf "Drone is flying to right" crlf)
    (assert (Fly (front ?p.front) (left ?p.left) (right ?p.right) (reverse FALSE) 
                (fly FALSE) (battery ?*b*) (fuel ?*f*)))
    else
    (assert (Stop (front ?p.front) (left ?p.left) (right ?p.right) (reverse ?p.reverse) 
                (fly ?p.fly) (battery ?*b*) (fuel ?*f*)))
    (printout t crlf "Battery or Fuel Low, Drone stopping!" crlf)
        (halt)))


(deffunction enterBatteryAndFuel(?action)
    (printout t crlf "Drone is trying to " ?action crlf)
    (printout t "Please enter Battery-level in terms of percentage, for 15% enter only 15" crlf)
    (bind ?*b* (read) t)
    (printout t "Please enter fuel-level in terms of percentage, for 15% enter only 15" crlf)
    (bind ?*f* (read) t)
    (if (and (> ?*b* 10) (> ?*f* 10)) then
        (return TRUE)
        else
        (return FALSE)))

(watch facts)
(reset)
(facts)
(focus startDrone afterStart)
(run)
(facts)
