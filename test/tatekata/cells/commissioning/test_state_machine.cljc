(ns tatekata.cells.commissioning.test-state-machine
  "tatekata 建方 commissioning state-machine tests."
  (:require [clojure.test :refer [deftest is]]
            [tatekata.cells.commissioning.state-machine :as sm]))

(deftest chain-reaches-end
  (let [out (sm/run-chain {})]
    (is (= "complete" (get-in out ["commissioning_state" "phase"])))
    (is (= 100 (get-in out ["commissioning_state" "completionPct"])))
    (is (= "end" (get out "next_node")))
    (is (contains? out "project_closure_record"))))
