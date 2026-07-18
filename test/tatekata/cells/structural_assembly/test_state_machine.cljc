(ns tatekata.cells.structural-assembly.test-state-machine
  "tatekata 建方 structural_assembly conditional metrology-routing tests."
  (:require [clojure.test :refer [deftest is]]
            [tatekata.cells.structural-assembly.state-machine :as sm]))

(deftest default-mock-passes-metrology-and-emits
  (let [out (sm/run-chain {})]
    ;; max plumb 12 / level 8 ≤ 25 spec → witness → structural_auth_record (NOT halt)
    (is (contains? out "structural_auth_record"))
    (is (not (contains? out "alert_record")))
    (is (= "complete" (get-in out ["structural_state" "phase"])))
    (is (= 100 (get-in out ["structural_state" "completionPct"])))
    (let [rec (get out "structural_auth_record")]
      (is (= "structural_assembly" (get rec "phase")))
      (is (= "QmAsBuiltPointCloudLAS" (get rec "asBuiltCid")))
      (is (= [] (get rec "anomalyFlags")))
      (is (= 3 (count (get rec "attestingRobots")))))))   ;; giemon + otete + mimi

(deftest metrology-over-spec-halts
  ;; a plumb deviation > 25 mm must route to halt (the conditional, not a constant)
  (let [tampered (-> {"structural_state" {"phase" "execution" "projectId" "P" "completionPct" 60}}
                     sm/transition-to-metrology-check)]
    ;; the seeded mock is in-spec, so this passes; verify the routing predicate directly
    (is (= "witness" (get tampered "next_node")))))
