(require 'clojure.test
         'tatekata.murakumo-test
         'tatekata.methods.test-agent
         'tatekata.methods.test-charter-gates
         'tatekata.cells.commissioning.test-state-machine
         'tatekata.cells.finishing-handoff.test-state-machine
         'tatekata.cells.foundation-excavation.test-state-machine
         'tatekata.cells.mep-installation.test-cell
         'tatekata.cells.structural-assembly.test-state-machine)

(let [result (apply clojure.test/run-tests
                    '[tatekata.murakumo-test
                      tatekata.methods.test-agent
                      tatekata.methods.test-charter-gates
                      tatekata.cells.commissioning.test-state-machine
                      tatekata.cells.finishing-handoff.test-state-machine
                      tatekata.cells.foundation-excavation.test-state-machine
                      tatekata.cells.mep-installation.test-cell
                      tatekata.cells.structural-assembly.test-state-machine])]
  (when-not (zero? (+ (:fail result) (:error result)))
    (throw (ex-info "tatekata tests failed" result))))
