(ns unit_test
    :require [clojure.test :refer [:deftest is run-tests]]
    [main :refer :all])
(deftest test_line_interpolation
         (let [points [(->Point 1 2) (->Point 3 4)]]
           (is (= (line_interpolation points 2) (->Point 2 3)))
           (is (= (line_interpolation points 1) (->Point 1 2)))))

(deftest test_lagrange_interpolation_based
         (let [points [(->Point 1 2) (->Point 2 3) (->Point 3 5)]]
           (is (= (Lagrange_interpolation_based points 2.5 1) 0.75))
           (is (= (Lagrange_interpolation_based points 0 0) 1))))
(deftest test_lagrange_interpolation
         (let [points [(->Point 1 1) (->Point 2 4) (->Point 3 9)]]
           (is (= (Lagrange_interpolation points 2) (->Point 2 4)))
           (is (= (Lagrange_interpolation points 1.5) (->Point 1.5 2.25)))))
(deftest test_interpolation
         (let [points [(->Point 1 1) (->Point 2 4) (->Point 3 9) (->Point 4 16)]]
          (is (= (interpolation points line_interpolation 1 2) [(->Point 3 9) (->Point 4 16)]))
          (is (= (interpolation points Lagrange_interpolation 1 3) [(->Point 2 4) (->Point 3 9) (->Point 4 16)]))))
(run-tests)
