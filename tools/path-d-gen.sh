
function create_name_json_list() {
    local prefix=$1
    local file=$2
    awk -v pfx=$prefix -f - $file <<'EOT'
    {
        split($1, elm, "/")
        if (length(elm)) {
            elements[length(elements)] = elm[length(elm)]
            paths[length(paths)] = $1
        }
    }
    END {
        for (idx = 0; idx < length(elements); idx++) {
            if (idx != 0 && idx < length(elements) - 1) {
                comma_or_blnc = ", "
            } else {
                comma_or_blnc = ""
            }
            if (pfx) {
                elem = (pfx "/" elements[idx])
            } else {
                elem = (elements[idx])
            }
            out_elem = "{\n"
            out_elem = (out_elem "\"name\": \"" elem "\",\n")
            out_elem = (out_elem "\"path\": \"" paths[idx] "\"\n")
            out_elem = (out_elem "}")
            if (idx == 0 && idx < length(elements) - 1) {
                out_elem = (out_elem ",")
            }
            out_elem = (out_elem "\n")
            outstr = (outstr comma_or_blnc out_elem)
        }
        print ("[\n" outstr "]")
    }
EOT
}

function print_help() {
    local prog_name=path-d-gen.sh
    cat <<EOT
$prog_name [OPTIONS]
OPTIONS
-p [PREFIX]     prefix for name of module.
-f [FILE]       list to load path data  
-h              print thid message.
EOT
}

while getopts p:f:h opt ; do
    case $opt in
        p)
            prefix=$OPTARG
            ;;
        f)
            data_list=$OPTARG
            ;;
        ?)
            print_help
            exit 1
            ;;
        h)
            print_help
            exit
            ;;
    esac
done

create_name_json_list $prefix $data_list

# vi: se ts=4 sw=4 et:
