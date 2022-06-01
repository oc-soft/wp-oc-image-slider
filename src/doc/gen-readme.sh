
function usage()
{
  echo "sh gen-index.sh [OPTIONS]
generate index.php from index.php.in
  -s [DIR]  Specify source directory
  -a [DIR]  Specify asset directrory
  -h        Display this help"
}

srcdir=.
assetsdir=../assets

while getopts s:a:m:j:h opt; do
  case "$opt" in
    s)
      srcdir=$OPTARG
      ;;
    a)
      assetsdir=$OPTARG
      ;;
    h|?|:)
      usage
      exit 0
      ;;
  esac
done

plugin_name=$(cat ${assetsdir}/plugin-name.txt)
requires_php=$(cat ${assetsdir}/requires-php.txt)
requires_wp=$(cat ${assetsdir}/requires-wp.txt)
version=$(cat ${assetsdir}/version.txt)


sed -e "s/@PLUGIN_NAME@/Plugin\ Name:\ $plugin_name/" \
  -e "s/@REQUIRES_PHP@/$requires_php/" \
  -e "s/@REQUIRES_WP@/$requires_wp/" \
  -e "s/@VERSION@/$version/" \
  ${srcdir}/readme.txt.in


# vi: se ts=4 sw=4 et:
