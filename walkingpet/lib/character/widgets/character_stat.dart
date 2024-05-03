import 'package:flutter/material.dart';

class CharacterInfoStat extends StatelessWidget {
  final String statname;
  final int point, addpoint;

  const CharacterInfoStat(
      {super.key,
      required this.statname,
      required this.point,
      required this.addpoint});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 7),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          // 1. 아이콘
          SizedBox(
            width: 25,
            // height: 20,
            child: Image.asset(_getImagePath(statname)),
          ),

          // SvgPicture.asset(
          //   'assets/icons/icon_health.svg',
          //   // width: 20,
          //   height: 20,
          // ),

          // 2. 체력 / 공격력 / 방어력
          SizedBox(
            width: 70,
            child: Text(
              statname,
              style: const TextStyle(
                fontSize: 20,
              ),
              textAlign: TextAlign.center,
            ),
          ),

          // 3. 능력치
          SizedBox(
            width: 95,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  point.toString(),
                  style: const TextStyle(
                    fontSize: 20,
                  ),
                ),
                const Text(
                  '(',
                  style: TextStyle(
                    fontSize: 17,
                  ),
                ),
                // Text(
                //   fix.toString(),
                //   style: const TextStyle(
                //     fontSize: 13,
                //   ),
                // ),
                const Text(
                  '+',
                  style: TextStyle(
                    fontSize: 13,
                    color: Color.fromARGB(255, 166, 39, 30),
                  ),
                ),
                Text(
                  addpoint.toString(),
                  style: const TextStyle(
                    fontSize: 16,
                    color: Color.fromARGB(255, 166, 39, 30),
                  ),
                ),
                const Text(
                  ')',
                  style: TextStyle(
                    fontSize: 17,
                  ),
                ),
              ],
            ),
          ),

          // 4. '+' 버튼
          SizedBox(
            // width: 30,
            child: Image.asset(
              'assets/buttons/yellow_plus_button.png',
              scale: 0.8,
            ),
            // child: Icon(
            //   Icons.add_box_sharp,
            //   color: Color.fromRGBO(251, 192, 45, 1),
            // ),
          ),
        ],
      ),
    );
  }

  String _getImagePath(String statname) {
    switch (statname) {
      case '체력':
        return 'assets/icons/icon_health.png';
      case '공격력':
        return 'assets/icons/icon_power.png';
      case '방어력':
        return 'assets/icons/icon_depense.png';
      default:
        return '';
    }
  }

  // IconData _getIconData(String statname) {
  //   switch (statname) {
  //     case '체력':
  //       return Icons.favorite;
  //     case '공격력':
  //       return Icons.flash_on;
  //     case '방어력':
  //       return Icons.shield;
  //     default:
  //       return Icons.error;
  //   }
  // }

  // IconData _getIconColor(statname) {
  //   switch (statname) {
  //     case '체력':
  //       return Colors.red;
  //     case '공격력':
  //       return Colors.yellow;
  //     case '방어력':
  //       return Colors.blue;
  //     default:
  //       return Colors.grey;
  //   }
  // }
}
