(ns tatekata.cells.finishing-handoff.test-state-machine
  "tatekata 建方 finishing-handoff state-machine tests."
  (:require [clojure.test :refer [deftest is]]
            [tatekata.cells.finishing-handoff.state-machine :as sm]))

(deftest chain-reaches-end
  (let [out (sm/run-chain {})]
    (is (= "complete" (get-in out ["finishing_state" "phase"])))
    (is (= 100 (get-in out ["finishing_state" "completionPct"])))
    (is (= "end" (get out "next_node")))
    (is (contains? out "finishing_record"))))
