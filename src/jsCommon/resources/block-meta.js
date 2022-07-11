

const meta = require('oc-soft/block.json');

module.exports.meta = meta

module.exports.name = meta.name

module.exports.createMeta = (additionalObj) => {
    return Object.assign({}, meta, additionalObj)
}
// vi: se ts=4 sw=4 et:
