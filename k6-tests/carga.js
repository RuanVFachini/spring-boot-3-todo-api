import http from 'k6/http';
import { check, group, sleep } from 'k6';
export const options = {
  stages: [
    { duration: '30s', target: 10000 },   // Ramp-up: até 20 usuários
    { duration: '1m', target: 5000 },    // Load: até 500 usuários
    { duration: '30s', target: 0 },    // Ramp-down
  ],
  thresholds: {
    'http_req_duration': ['p(95)<2000'],   // 95% deve ser < 2s
    'http_req_failed': ['rate<0.1'],       // Taxa de erro < 10%
    'group_duration{group:::checkout}': ['p(95)<3000'], // Fluxo completo < 3s
  },
};
const BASE_URL = 'http://host.docker.internal:8080';
export default function () {
  
  group('Todo creation', function () {
    
    // 1. Realiza loging
    const login = http.post(`${BASE_URL}/api/auth/login`, JSON.stringify({
      "email": "teste1",
      "password": "teste1"
    }), {
      headers: { 'Content-Type': 'application/json' },
    });
    check(login, {
      'login realizado': (r) => r.status === 200,
      'latência < 3000ms': (r) => r.timings.duration < 3000,
    });
    sleep(1);

    // 2. Criar todo
    const todo = http.post(`${BASE_URL}/api/todos`, JSON.stringify({
      description: "item 1"
    }), {
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + login.json().token
      },
    });
    check(todo, {
      'todo criado': (r) => r.status === 200,
      'latência < 3000ms': (r) => r.timings.duration < 3000,
    });
    sleep(1);
  });
}