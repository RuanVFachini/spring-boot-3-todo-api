#!/usr/bin/env bash

set -euo pipefail

# --- cores (opcional) ---
RED="\033[0;31m"
GREEN="\033[0;32m"
NC="\033[0m"


rodar_testes_de_carga() {
  echo -e "${GREEN}Rodando testes k6...${NC}"
  docker run --rm -i \
  -v $(pwd):/app \
  --add-host=host.docker.internal:host-gateway \
  grafana/k6 run /app/k6-tests/carga.js
}

sair() {
  echo "Saindo..."
  exit 0
}

# --- menu ---
mostrar_menu() {
  clear
  echo "========================="
  echo "  UTILITÁRIO DEV"
  echo "========================="
  echo -e "1) ${GREEN}Testes de carga com k6...${NC}"
  echo "0) Sair"
  echo "-------------------------"
}

# --- loop principal ---
while true; do
  mostrar_menu
  read -rp "Escolha uma opção: " opcao

  case "$opcao" in
    1) rodar_testes_de_carga ;;
    0) sair ;;
    *) echo -e "${RED}Opção inválida${NC}" ;;
  esac

  echo ""
  read -rp "Pressione ENTER para continuar..."
done