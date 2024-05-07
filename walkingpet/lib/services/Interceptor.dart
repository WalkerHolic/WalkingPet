import 'package:http/http.dart' as http;
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class AuthInterceptor extends http.BaseClient {
  final http.Client _inner = http.Client();
  final FlutterSecureStorage _storage = FlutterSecureStorage();

  @override
  Future<http.StreamedResponse> send(http.BaseRequest request) async {
    final accessToken = await _storage.read(key: 'ACCESS_TOKEN');

    final headers = request.headers;
    headers['Authorization'] = 'Bearer $accessToken';
    request.headers.addAll(headers);

    return _inner.send(request);
  }
}
