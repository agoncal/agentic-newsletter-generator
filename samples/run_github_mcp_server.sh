#!/usr/bin/env bash

docker run \
  -e GITHUB_PERSONAL_ACCESS_TOKEN=<your_github_token> \
  -e GITHUB_TOOLSETS="repos" \
  -e GITHUB_READ_ONLY=1 \
  -i --rm ghcr.io/github/github-mcp-server