import 'package:http/http.dart' as http;
import 'dart:convert';

Future<Map<String, dynamic>> getGroupDetail(groupId) async {
  final response = await http
      .get(Uri.parse('https://walkingpet.co.kr/team/detail/$groupId'));
  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    var jsonData = jsonDecode(data);
    print(jsonData['data']);
    return jsonData['data'];
  } else {
    print(response.statusCode);
    throw Error();
  }
}
