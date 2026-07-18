(ns tatekata.cells.foundation-excavation.test-state-machine
  "tatekata 建方 foundation_excavation conditional anomaly-routing tests."
  (:require [clojure.test :refer [deftest is]]
            [tatekata.cells.foundation-excavation.state-machine :as sm]))

(deftest default-mock-halts-on-vibration-anomaly
  (let [out (sm/run-chain {})]
    ;; vibration 2.1 g > 2.0 g → ANOMALY_HALT → alert_record (NOT the witness path)
    (is (contains? out "alert_record"))
    (is (not (contains? out "constructed_record")))
    (is (= "anomaly_halt" (get-in out ["foundation_state" "phase"])))
    (is (= ["vibration_spike_2.1g"] (get-in out ["foundation_state" "anomalyFlags"])))
    (is (= "construction_halt" (get-in out ["alert_record" "event"])))
    (is (= ["vibration_spike_2.1g"] (get-in out ["alert_record" "anomalies"])))))

(deftest no-anomaly-path-reaches-emit
  ;; if the anomaly check finds nothing, the witness→emit path runs (constructed_record)
  (let [clean (-> {"foundation_state" {"phase" "execution" "siteId" "S" "completionPct" 75
                                       "anomalyFlags" [] "photoCid" "p" "depthMapCid" "d"}
                   "next_node" "witness_attestation"}
                  sm/wait-for-witness-sigs sm/emit-progress-record)]
    (is (contains? clean "constructed_record"))
    (is (= ["did:web:etzhayyim.com:giemon-unit-1" "did:web:etzhayyim.com:otete-unit-2"]
           (get-in clean ["constructed_record" "attestingRobots"])))))   ;; list of DIDs, not dicts
