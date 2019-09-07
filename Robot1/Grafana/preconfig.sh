#!/bin/bash

# A script to configure grafana datasource and dashboards.


GRAFANA_URL=${GRAFANA_URL:-http://$GF_SECURITY_ADMIN_USER:$GF_SECURITY_ADMIN_PASSWORD@localhost:3000}
DATASOURCE_PATH=${DATASOURCE_PATH:-/etc/Grafana/datasource}
DASHBOARDS_PATH=${DASHBOARDS_PATH:-/etc/Grafana/dashboards}

# Generic function to call the Vault API
grafana_api() {
  local verb=$1
  local url=$2
  local params=$3
  local bodyfile=$4
  local response
  local cmd

  cmd="curl -L -s --fail -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X ${verb} -k ${GRAFANA_URL}${url}"
  [[ -n "${params}" ]] && cmd="${cmd} -d \"${params}\""
  [[ -n "${bodyfile}" ]] && cmd="${cmd} --data @${bodyfile}"
  echo "Running ${cmd}"
  eval ${cmd} || return 1
  return 0
}

waitToCallApi() {
  while ! grafana_api GET /api/user/preferences
  do
    sleep 2
  done 
}

installDatasourceAndDashboards() {
  local datasource
  local dashboard

  for datasource in ${DATASOURCE_PATH}/*.json
  do
    if [[ -f "${datasource}" ]]; then
      echo "Installing datasource ${datasource}"
      if grafana_api POST /api/datasources "" "${datasource}"; then
        echo "installed ok"
      else
        echo "install failed"
      fi
    fi
  done

  for dashboard in ${DASHBOARDS_PATH}/*.json
  do
    if [[ -f "${dashboard}" ]]; then
      echo "Installing dashboard ${dashboard}"

      if grafana_api POST /api/dashboards/db "" "${dashboard}"; then
        echo "installed ok"
      else
        echo "install failed"
      fi

    fi
  done
}

configuritation() {
  waitToCallApi
  installDatasourceAndDashboards
}

echo "***Configuring grafana datasource and dashboard***"
configuritation
exit 0
