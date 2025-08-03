import { readFileSync } from "node:fs";

export default {

  watch: ['gradle.properties'],

  load() {

    const file = readFileSync('gradle.properties', 'utf8');

    const lines = file.split('\n');
    const versionEntry = lines.find(line => line.startsWith('version='));

    if (!versionEntry) {
      throw new Error('Version not found in gradle.properties');
    }

    const version = versionEntry.split('=')[1];

    if (!version) {
      throw new Error('Version number not found in gradle.properties');
    }

    return { version }
  }
}
