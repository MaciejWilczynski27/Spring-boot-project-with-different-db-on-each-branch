#!/bin/bash

mongo <<EOF
rs.initiate();
EOF