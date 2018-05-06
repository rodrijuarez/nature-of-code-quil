(ns nature-of-code-clojure.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(use 'clojure.core.matrix)
(use 'clojure.core.matrix.operators)
(set-current-implementation :vectorz)

(defn random-vec [min-a max-a min-b max-b]
  (let [c (vector (q/random min-a max-a) (q/random min-b max-b))] c))

(defn constrain-vector [a min-a max-a min-b max-b]
  (let [constrained-vector (vector (q/constrain (nth a 0) min-a max-a) (q/constrain (nth a 1) min-b max-b))] constrained-vector))

(defn setup []
  (q/frame-rate 30)
  {:snake-location (vector (q/width) (q/height))
   :snake-velocity (vector 0.1 0.1)})

(defn update-state [state]
  (let [mouse (vector (q/mouse-x) (q/mouse-y))
        snake-location (:snake-location state)
        direction (* (normalise (- mouse (:snake-location state))) 0.5)
        snake-velocity (constrain-vector (+ direction (:snake-velocity state)) -1 1 -5 5)
        snake-location (+ snake-velocity snake-location)]
    (assoc state :snake-location snake-location :snake-velocity snake-velocity)))

(defn draw-state [state]
  (q/background 240)
  (q/ellipse (nth (:snake-location state) 0) (nth (:snake-location state) 1) 16 16))

(q/defsketch nature-of-code-clojure
  :title "Eco eco eco"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])

