# com-etzhayyim-tatekata repository rules

- This is an independent flat-path west repository.
- EDN is canonical. Do not commit JSON, JSON-LD, or BPMN outside a declared
  `wire/` directory.
- Keep code in `src/tatekata/`, tests in `test/tatekata/`, cell configuration in
  `config/cells/`, actor-owned schema in `schema/`, and seed state in `data/`.
- Do not reintroduce Go, TinyGo, shell launchers, Python parity ports, or former
  monorepo-relative paths.
- Preserve every constitutional gate and non-goal in `manifest.edn`.
- Run `bb test` before publishing changes.
