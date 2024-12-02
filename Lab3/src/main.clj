(ns main
  (:require [clojure.string :as str]))

(defrecord Point [x y])

(defn input []
      (println "Введите x и y")
  (let [line (read-line)]
    (let [parts (str/split line #"\s+")]
      (if (= (count parts) 2)
        (try
          (let [x (Double/parseDouble (first parts))
                y (Double/parseDouble (second parts))]
            (Point. x y))
          (catch Exception e
            (println "Неправильный ввод")
            (input)))
        (do
         (println "Неправильный ввод")
         (input))
        ))))
(defn Lagrange_interpolation_based [points x i]
  (loop [j 0 L 1]
    (if (< j (count points))
      (recur (inc j)  (if (= j i) L (* L (/ (- x (:x (nth points j))) (- (:x (nth points i)) (:x (nth points j)))))))
      L)))
(defn Lagrange_interpolation [points x]
  (loop [y 0 i 0]
    (if (< i (count points))
      (recur (+ y (* (:y (nth points i)) (Lagrange_interpolation_based points x i))) (inc i))
      (Point. x y))))
(defn line_interpolation [points x]
  (let [[point1 point2] (take-last 2 points)
        a (/ (- (:y point2) (:y point1)) (- (:x point2) (:x point1)))
        b (- (:y point1) (* a (:x point1)))
        y (+ (* a x) b)]
    (Point. x y)))
(defn interpolation [points interpolation_defn step count_last_point]
  (let [points_for_interpolation (take-last count_last_point points)
        min_x (:x (first points_for_interpolation))
        max_x (:x (last points_for_interpolation))]
    (loop [x min_x interpolation_points []]
      (if (>= x (+ max_x step))
        interpolation_points
        (recur (+ x step) (conj interpolation_points (interpolation_defn points x)))))))
(defn print_interpolation [points alg]
      (if (= alg "line") (println "Линейная Интерполяция") (println "Интерполяция Лагранжа"))
  (let [x (map :x points)]
    (println (clojure.string/join "\t" x)))
  (let [y (map :y points)]
    (println (clojure.string/join "\t" y))))
(defn -main [& args]
  (if (and (= (count args) 2) (or (= (nth args 0) "line") (= (nth args 0) "lagrange") (= (nth args 0) "all")) (int? (Integer/parseInt (nth args 1))))
    (loop [points []]
      (let [new_point (input)]
        (let [new_points (if (> (count points) (inc 2))
                           (rest (conj points new_point))
                           (conj points new_point))
              line (or (= (nth args 0) "line") (= (nth args 0) "all"))
              lagrange (or (= (nth args 0) "lagrange") (= (nth args 0) "all"))
              step (Integer/parseInt (nth args 1))]
          (when (and (> (count new_points) 1) line)(print_interpolation (interpolation new_points line_interpolation step 2) "line"))
          (when (and (> (count new_points) 3) lagrange)(print_interpolation (interpolation new_points Lagrange_interpolation step 4) "lagrange"))
          (recur new_points))))
    (println "Неверный ввод аттрибутов"))
  )
