# Executar teste de carga:

```bash
docker run --rm -i \
  -v $(pwd):/app \
  --add-host=host.docker.internal:host-gateway \
  grafana/k6 run /app/k6-tests/carga.js
```