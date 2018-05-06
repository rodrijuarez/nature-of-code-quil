(ns nature-of-code-clojure.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(use 'clojure.core.matrix)
(use 'clojure.core.matrix.operators)
(set-current-implementation :vectorz)

(defn constrain-vector [a min max]
  (let [constrained-vector (vector (q/constrain (nth a 0) min max) (q/constrain (nth a 1) min max))] constrained-vector))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  {:mouse (vector 0 0)
   :velocity (vector 0 0)
   :topSpeed 10
   :location (vector (/ (q/width) 2) (/ (q/height) 2))})

(defn update-state [state]
  (let [mouse (vector (q/mouse-x) (q/mouse-y))
        location (:location state)
        direction (- mouse location)
        dir (* (normalise direction) 0.5)
        new-velocity (constrain-vector (+ dir (:velocity state)) -10 10)
        new-location (+ new-velocity location)]
    (assoc state :mouse mouse :velocity new-velocity :location new-location)))

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  (q/ellipse (nth (:location state) 0) (nth (:location state) 1) 16 16))

(q/defsketch nature-of-code-clojure
  :title "You spin my circle right round"
  :size [500 500]
    ; setup function called only once, during sketch initialization.
  :setup setup
    ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
    ; This sketch uses functional-mode middleware.
    ; Check quil wiki for more info about middlewares and particularly
    ; fun-mode.
  :middleware [m/fun-mode])
