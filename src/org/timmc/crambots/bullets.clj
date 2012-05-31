(ns org.timmc.crambots.bullets
  "Flexibly scored bot that prefers (in descending order) moves that
have open space above and below, on odd-numbered rows, starting on even-
numbered columns, on the right side of the board."
  (:require [crosscram.game :as cc])
  (:use [org.timmc.handy :only (lexicomp)]))

(defn canonical
  "Order a horizontal piece in left to right square order."
  [piece]
  (let [[[row c0] [_ c1]] piece
        left (min c0 c1)]
    [[row left] [row (inc left)]]))

(defn neighborhood
  "Get board values for a neighborhood centered on a possible move with
`rad-r` rows and `rad-c` cols around it. Returns a vector of row vectors
containing the values from the board."
  [board [rad-r rad-c] move]
  (let [[[row left] _] (canonical move)]
    (vec (for [y (range (- row rad-r) (+ row rad-r 1))]
           (vec (for [x (range (- left rad-c) (+ left rad-c 2))]
                  (cc/lookup-square board [y x])))))))

(defn score-rows-open
  "Score [1 0] neighborhood based on open space above and below."
  [hood]
  (let [[above _ below] hood]
    (+ (if (= above [nil nil]) 1 0)
       (if (= below [nil nil]) 1 0))))

(defn score
  "Score a candidate. Produces a score vector, to be sorted with others
lexicographically."
  [board move]
  (let [[[r0 c0] [_ c1]] (canonical move)
        hood (neighborhood board [1 0] move)]
    [(score-rows-open hood) ;; prefer to gain space
     (if (odd? r0) 1 0)     ;; most importantly, use odd rows
     (if (even? c0) 1 0)    ;; even columns give better filling of rows
     c0                     ;; prefer the right side of the board (tie-break)
     ]))

(defn candidates
  [board]
  (for [move (cc/available-moves board)]
    {:move move, :score (score board move)}))

(defn make-move [game]
  (->> (candidates (:board game))
       (sort-by :score (comp - lexicomp))
       (partition-by :score)
       (drop-while empty?)
       first
       rand-nth
       :move))
