#! /usr/bin/env sh



function usage()
{
  echo "sh gen-index.sh [OPTIONS]
generate index.php from index.php.in
  -s [DIR]  Specify source directory
  -a [DIR]  Specify asset directrory
  -m [FILE] message object file for translation
  -j [NUM]  indent number of message object file 
  -h        Display this help"
}

srcdir=.
assetsdir=../assets
trans=
trans_idt_num=0

while getopts s:a:m:j:h opt; do
  case "$opt" in
    s)
      srcdir=$OPTARG
      ;;
    a)
      assetsdir=$OPTARG
      ;;
    m)
      if [ "x$trans" == "x" ]; then
        trans=\'$OPTARG\'
      else
        trans=$trans\ \'$OPTARG\'
      fi
      ;;
    j)
      trans_idt_num=$OPTARG
      ;;
    h|?|:)
      usage
      exit 0
      ;;
  esac
done

trans_idt=

for (( idx=0 ; $idx < $trans_idt_num ; idx++ )) ; do
  {
    trans_idt=\ $trans_idt 
  };
done

trans_str=
for elem in $trans ; do
  {
    if [ "x$trans_str" == "x" ] ; then
      trans_str=${trans_idt}${elem}
    else
      trans_str=${trans_str}\\n${trans_idt}${elem}
    fi
  };
done

plugin_name=$(gettext 'Plugin Name')
desc_key=$(gettext 'Description')
author_key=$(gettext 'Author')
version_key=$(gettext 'Version')

desc_value=$(cat ${assetsdir}/description.txt)
author_value=$(cat ${assetsdir}/author.txt)
plugin_name=$(cat ${assetsdir}/plugin-name.txt)
version_value=$(cat ${assetsdir}/version.txt)
requires_php=$(cat ${assetsdir}/requires-php.txt)
requires_wp=$(cat ${assetsdir}/requires-wp.txt)

sed -e "s/@PLUGIN_NAME@/Plugin\ Name:\ $plugin_name/" \
  -e "s/@AUTHOR@/Author:\ $author_value/" \
  -e "s/@DESCRIPTION@/Description:\ $desc_value/" \
  -e "s/@VERSION@/Version:\ $version_value/" \
  -e "s/@REQUIRES_PHP@/$requires_php/" \
  -e "s/@REQUIRES_WP@/$requires_wp/" \
  -e "s/@TRANSLATIONS@/$trans_str/" ${srcdir}/index.php.in

# vi: se ts=2 sw=2 et:
